package UI;

import Domain.Pacient;
import Domain.Programare;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.NonExistentPatientException;
import Exceptions.ObjectNotFoundException;
import Service.Service;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Console {
    Service serv;
    public Console(Service serv)
    {
        this.serv = serv;
    }

    public void displayAppointmentsCountPerPatient(){
        Service.displayAppointmentsCountPerPatient((List<Programare>) this.serv.getAll_programare(), (List<Pacient>) this.serv.getAll_pacient());
    }
    public void raportZileTrecuteDeLaUltimaProgramare(){
        this.serv.raportZileTrecuteDeLaUltimaProgramare();
    }
    public void runMenu()
    {
        while(true)
        {
           printMenu();
           String option;
           Scanner scanner = new Scanner(System.in);
           option = scanner.next();
           switch (option){
               case "1":
               {
                   try{
                       int id = scanner.nextInt();
                       String nume = scanner.next();
                       String prenume = scanner.next();
                       int varsta = scanner.nextInt();
                       serv.add_pacient(id, nume,prenume,varsta);
                   }
                   catch (DuplicateEntityExceptions ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "2":
               {
                   Collection<Pacient> pacienti = serv.getAll_pacient();
                   for(Pacient p: pacienti)
                       System.out.println(p);
                   break;
               }
               case "3":
               {
                   try{
                       int id = scanner.nextInt();
                       serv.remove_pacient(id);
                   }
                   catch (ObjectNotFoundException ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "4":
               {
                   try{
                       int id = scanner.nextInt();
                       String nume = scanner.next();
                       String prenume = scanner.next();
                       int varsta = scanner.nextInt();
                       serv.update_pacient(id, nume, prenume, varsta);
                   }
                   catch (ObjectNotFoundException ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "5":
               {
                   try{
                       int id = scanner.nextInt();
                       int id_pacient = scanner.nextInt();
                       String data = scanner.next();
                       String ora = scanner.next();
                       String scop = scanner.next();
                       serv.add_programare(id, id_pacient, data, ora, scop);
                   }
                   catch (DuplicateEntityExceptions ex){
                       System.out.println(ex.toString());
                   }
                   catch(NonExistentPatientException ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "6":
               {
                   Collection<Programare> programari = serv.getAll_programare();
                   for(Programare p: programari)
                       System.out.println(p);
                   break;
               }
               case "7":
               {
                   try{
                       int id = scanner.nextInt();
                       serv.remove_programare(id);
                   }
                   catch (ObjectNotFoundException ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "8":
               {
                   try{
                       int id = scanner.nextInt();
                       int id_pacient = scanner.nextInt();
                       String data = scanner.next();
                       String ora = scanner.next();
                       String scop = scanner.next();
                       serv.update_programare(id,id_pacient,data,ora,scop);
                   }
                   catch (ObjectNotFoundException ex){
                       System.out.println(ex.toString());
                   }
                   catch(NonExistentPatientException ex){
                       System.out.println(ex.toString());
                   }
                   catch (Exception ex){
                       System.out.println(ex.toString());
                   }
                   break;
               }
               case "9":
                   displayAppointmentsCountPerPatient();
                   break;
               case "10":
                   this.serv.raportNumarProgramariPeLuna();
                   break;
               case "11":
                   raportZileTrecuteDeLaUltimaProgramare();
                   break;
               case "x":
               {
                   return;
               }
               default:{
                   System.out.println("Optiune gresita. Reincearca: ");
               }
           }
        }
    }
    private void printMenu()
    {
        System.out.println("1. Adauga pacient. ");
        System.out.println("2. Afiseaza toti pacientii.");
        System.out.println("3. Sterge pacient. ");
        System.out.println("4. Modifica pacient.");
        System.out.println("5. Adauga programare.");
        System.out.println("6. Afiseaza toate programarile.");
        System.out.println("7. Sterge programare. ");
        System.out.println("8. Modifica programare.");
        System.out.println("9. Numărul de programări pentru fiecare pacient în parte.");
        System.out.println("10. Numărul total de programări pentru fiecare lună a anului.");
        System.out.println("11. Numărul de zile trecute de la ultima programare a fiecărui pacient.");
        System.out.println("x. Exit. ");

    }
}
