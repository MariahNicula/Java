package Main;

import Domain.*;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.NonExistentPatientException;
import Exceptions.ObjectNotFoundException;
import Properties.SettingsReader;
import Repository.BinaryFileRepository;
import Repository.PacientBDRepo;
import Repository.ProgramareBDRepo;
import Repository.FileRepository;
import Repository.IRepository;
import Repository.Repository;
import Service.Service;
import UI.Console;

import java.io.IOException;
class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ObjectNotFoundException, NonExistentPatientException, DuplicateEntityExceptions {
        //TestAll testAll=new TestAll();
        //testAll.testAll();

        SettingsReader settingsReader = new SettingsReader("settings.properties");
        String repositoryType = settingsReader.getRepositoryType();
        String patientsFileName = settingsReader.getPatientsFileName();
        String appointmentsFileName = settingsReader.getAppointmentsFileName();
        System.out.println("Repository Type: " + repositoryType);
        System.out.println("Patients File: " + patientsFileName);
        System.out.println("Appointments File: " + appointmentsFileName);
        if (repositoryType.equals("binary"))
        {
            IRepository<Pacient> pacientRepository= new BinaryFileRepository<Pacient>("data.bin");
            IRepository<Programare> programareRepository= new BinaryFileRepository<Programare>("data_programari.bin");
            Service service = new Service(pacientRepository, programareRepository);
            Console ui = new Console(service);
            ui.runMenu();
        }
        if (repositoryType.equals("text"))
        {
            IEntityFactory<Pacient> pacientFactory=new PacientFactory();
            IRepository<Pacient> repo_pacient=new FileRepository<>("pacienti.txt",pacientFactory);
            IEntityFactory<Programare> programareFactory=new ProgramareFactory();
            IRepository<Programare> repo_programare=new FileRepository<>("programari.txt",programareFactory);
            Service service = new Service(repo_pacient, repo_programare);
            Console ui = new Console(service);
            ui.runMenu();
        }
        if (repositoryType.equals("console"))
        {
            IRepository<Pacient> repo_pacient = new Repository<Pacient>();
            Pacient pacient1 = new Pacient(1,"Marinescu","Paul",19);
            repo_pacient.add_pacient(pacient1);
            Pacient pacient2 = new Pacient(2,"Nicola","Pavel",20);
            repo_pacient.add_pacient(pacient2);
            Pacient pacient3 = new Pacient(3,"Popescu","Eduard",19);
            repo_pacient.add_pacient(pacient3);
            Pacient pacient4 = new Pacient(4,"Olteanu","Ana",20);
            repo_pacient.add_pacient(pacient4);
            Pacient pacient5 = new Pacient(5,"Candrea","Maria",21);
            repo_pacient.add_pacient(pacient5);


            IRepository<Programare> repo_programare = new Repository<Programare>();
            repo_programare.add_pacient(new Programare(1,(pacient1.getId()),"10/10/2023", "10:00", "Migrene"));
            repo_programare.add_pacient(new Programare(2,pacient2.getId(),"20/07/2023", "10:00", "EKG"));
            repo_programare.add_pacient(new Programare(3,pacient3.getId(),"5/02/2023", "10:00", "Depresie"));
            repo_programare.add_pacient(new Programare(4,pacient4.getId(),"18/05/2023", "10:00", "Evaluare"));
            repo_programare.add_pacient(new Programare(5,pacient5.getId(),"3/12/2023", "10:00", "Ghips picior"));
            Service service = new Service(repo_pacient, repo_programare);
            Console ui = new Console(service);
            ui.runMenu();
        }
        if(repositoryType.equals("database"))
        {
            IRepository<Pacient> pacientRepository= new PacientBDRepo();
            IRepository<Programare> programareRepository= new ProgramareBDRepo();
            Service service = new Service(pacientRepository, programareRepository);
            Console ui = new Console(service);
            ui.runMenu();
        }


    }
}
