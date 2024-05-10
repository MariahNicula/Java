package Service;

import Domain.Pacient;
import Domain.Programare;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.NonExistentPatientException;
import Exceptions.ObjectNotFoundException;
import Repository.IRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    IRepository<Pacient> repo_pacient;
    IRepository<Programare> repo_programare;

    public Service(IRepository<Pacient> repo_pacient, IRepository<Programare> repo_programare)
    {
        this.repo_pacient = repo_pacient;
        this.repo_programare = repo_programare;
    }

    //PACIENT
    public void add_pacient(int id, String nume, String prenume, int varsta) throws DuplicateEntityExceptions {
        repo_pacient.add_pacient(new Pacient(id,nume,prenume,varsta));
    }

    public Collection<Pacient> getAll_pacient()
    {
        return repo_pacient.getAll();
    }

    public void remove_pacient(int id) throws ObjectNotFoundException, IOException {
        repo_pacient.remove(id);
    }

    public void update_pacient(int id, String nume, String prenume, int varsta) throws ObjectNotFoundException {
        repo_pacient.update(new Pacient(id,nume,prenume,varsta));
    }

    public Pacient getPacient_byId(int id)
    {
        return repo_pacient.find(id);
    }

    public int getIdPacient(Pacient p)
    {
        return p.getId();
    }

    //PROGRAMARE
    public void add_programare(int id, int id_pacient, String data, String ora, String scop) throws DuplicateEntityExceptions, NonExistentPatientException {
        if(repo_pacient.find(id_pacient) == null)
        {
            throw new NonExistentPatientException("Pacient inexistent! ");
        }
        repo_programare.add_pacient(new Programare(id,id_pacient,data,ora,scop));
    }

    public void remove_programare(int id) throws ObjectNotFoundException,IOException {
        repo_programare.remove(id);
    }

    public Collection<Programare> getAll_programare()
    {
        return repo_programare.getAll();
    }

    public void update_programare(int id, int id_pacient, String data, String ora, String scop) throws ObjectNotFoundException, NonExistentPatientException {
        if(repo_pacient.find(id_pacient) == null)
        {
            throw new NonExistentPatientException("Pacient inexistent! ");
        }
        repo_programare.update(new Programare(id,id_pacient,data,ora,scop));
    }

    //problema 1
    public static void displayAppointmentsCountPerPatient(List<Programare> programari, List<Pacient> pacienti) {
        // Count the number of appointments for each patient using Java 8 streams
        Map<Integer, Long> appointmentsCountMap = programari.stream()
                .collect(Collectors.groupingBy(Programare::getPacient, Collectors.counting()));

        // Sort the patients by the number of appointments in descending order
        appointmentsCountMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .forEach(entry -> displayPatientInfo(entry.getKey(), entry.getValue().intValue(), pacienti));
    }

    private static void displayPatientInfo(int patientId, int appointmentsCount, List<Pacient> pacienti) {
        // Find the patient based on the ID
        pacienti.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst()
                .ifPresent(patient -> {
                    // Display patient information
                    System.out.println("Patient: " + patient.getNume() + " " + patient.getPrenume());
                    System.out.println("Patient ID: " + patient.getId());
                    System.out.println("Total Appointments: " + appointmentsCount);
                    System.out.println("---------------");
                });
    }


    // Problema 2: Numarul total de programari pentru fiecare luna a anului
    public void raportNumarProgramariPeLuna() {
        List<Programare> programari = ( List<Programare> )getAll_programare();
        // Definirea unui format pentru parsarea datelor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Gruparea programarilor dupa luna
        Map<Month, Long> numarProgramariPeLuna = programari.stream()
                .collect(Collectors.groupingBy(programare -> {
                    LocalDate dataProgramare = LocalDate.parse(programare.getData(), formatter);
                    return dataProgramare.getMonth();
                }, Collectors.counting()));

        // Sortarea rezultatelor în ordine descrescatoare
        numarProgramariPeLuna.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                    Month luna = entry.getKey();
                    Long numarProgramari = entry.getValue();
                    String numeLuna = luna.getDisplayName(TextStyle.FULL, Locale.getDefault());
                    System.out.println("Luna: " + numeLuna + ", Numar total de programari: " + numarProgramari);
                });
    }

    // Problema 3: Numarul de zile trecute de la ultima programare a fiecarui pacient
    public void raportZileTrecuteDeLaUltimaProgramare() {
        List<Programare> programari = (List<Programare>) getAll_programare();
        // Definirea unui format pentru parsarea datelor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        // Gruparea programarilor dupa id-ul pacientului
        Map<Integer, String> ultimaProgramarePentruPacient = programari.stream()
                .collect(Collectors.groupingBy(Programare::getPacient,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing((Programare p) -> LocalDate.parse(p.getData(), formatter))),
                                opt -> opt.map(Programare::getData).orElse("01/01/2000")
                        )
                ));

        // Calcularea numarului de zile trecute de la ultima programare si sortarea rezultatelor
        ultimaProgramarePentruPacient.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    LocalDate date1 = LocalDate.parse(entry1.getValue(), formatter);
                    LocalDate date2 = LocalDate.parse(entry2.getValue(), formatter);
                    return date2.compareTo(date1);
                })
                .forEach(entry -> {
                    int pacientId = entry.getKey();
                    String ultimaProgramare = entry.getValue();
                    long zileTrecute = LocalDate.parse(ultimaProgramare, formatter).until(LocalDate.now()).getDays();
                    // Assume you have a method to retrieve Pacient based on ID, replace getPatientById with your actual method
                    Pacient pacient = getPacient_byId(pacientId);
                    if (pacient != null) {
                        System.out.println("Pacient: " + pacient.getNume() + " " + pacient.getPrenume() +
                                ", Ultima programare: " + ultimaProgramare +
                                ", Zile trecute: " + zileTrecute);
                    }
                });
    }

}
