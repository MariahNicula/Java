package Repository;

import Domain.Entity;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;

import java.io.*;


public class BinaryFileRepository<T extends Entity> extends Repository<T> {
    private String filename;

    public BinaryFileRepository(String filename) throws IOException, ClassNotFoundException {
        this.filename = filename;
        loadFile();
    }

    @Override
    public void add_pacient(T o) throws DuplicateEntityExceptions {
        super.add_pacient(o);
        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            saveFile();
        } catch (Exception e) {
            throw new DuplicateEntityExceptions("Error saving file " + e.getMessage());
        }
    }

    public void remove(int entity) throws ObjectNotFoundException, IOException {
        super.remove(entity);
        saveFile();
    }

    public void update(T entity) throws ObjectNotFoundException {
        super.update(entity);
        try {
            saveFile();
        } catch (Exception e) {
            throw new ObjectNotFoundException("Error saving object" + e.getMessage());
        }
    }

    public T find(int id) {
        return super.find(id);
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Object entity = null;
            while (true) {
                try {
                    entity = objectInputStream.readObject();
                    entities.add((T) entity);
                } catch (EOFException eofException) {
                    break;
                } catch (ClassCastException e) {
                    System.err.println("ClassCastException: " + e.getMessage());
                    System.err.println("Object class: " + entity.getClass().getName());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Ignorați în cazul în care fișierul nu există sau conține date corupte
            System.out.println(e.toString());
        }
    }

    private void saveFile() throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (T entity : super.getAll()) {
                objectOutputStream.writeObject(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//private void saveFile() throws IOException {
//try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
//    Collection<T> entities = getAll();
//      oos.writeObject(entities);
//    }
//  }
//}
//
//    private void loadFile()
//    {
//            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
//                while (true) {
//                    try {
//                        T object = (T) ois.readObject();
//                        super.add(object);
//                    } catch (EOFException e) {
//                        // Am ajuns la sfârșitul fișierului
//                        break;
//                    }
//                }
//            } catch (IOException | ClassNotFoundException | RepositoryException e) {
//                e.printStackTrace();
//            }
//    }
//
//    private void saveFile() throws IOException {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
//            // Scrie obiectul in fisier
//            oos.writeObject(getAll()); // getAll() ar trebui să returneze o colecție cu toate obiectele din repository
//        }
//        catch (NotSerializableException e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//    private void loadFile()
//    {
//        System.out.println("load file Binary");
//
//        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
//        {
//            String data=(String) ois.readObject();
//            System.out.println(data);
//            T entity= (T) entityFactory.createEntity(data);
//            add(entity);
//            System.out.println("reading");
//        } catch (IOException | ClassNotFoundException | RepositoryException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    private void saveFile() throws IOException
//    {
//        // FIXME try with resources
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
//        oos.writeObject(oos);
//    }
//
//}
//

//
//public class BinaryFileRepository<T extends  Entity> extends Repository<T>
//{
//    private String fileName;
//    private IEntityFactory entityFactory;
//    public BinaryFileRepository(String fileName, IEntityFactory entityFactory) throws FileNotFoundException {
//        this.fileName=fileName;
//        this.entityFactory=entityFactory;
//       // writeTofile(fileName);
//       // readFromFile(fileName);
//    }
//
////    public void add(Pacient o) throws RepositoryExceptions  {
////        super.add(o);
////        // saveFile se executa doar daca super.add() nu a aruncat exceptie
////        try {
////            saveFile();
////        } catch (IOException e) {
////            throw new RepositoryException("Error saving file " + e.getMessage());
////        }
////    }
////    private void saveFile() throws IOException {
////        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.bin"))){
////            oos.writeObject(data);
////        }
////
////    }
////    private void loadFile() throws FileNotFoundException , ClassNotFoundException {
////        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))){
////            ois.readObject();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////
////    }
//
//    private void writeTofile(String fileName) throws FileNotFoundException {
//        try {
//            FileOutputStream fileOut = new FileOutputStream(fileName);
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            //Pacient p=new Pacient(45,"ion","ana",70);
//            ArrayList<T> al=super.getAll();
//            //String data= "25,ion,ana,70";
//            //String data1= "26,ana,ionel,71";
//            for (int i = 0; i < al.size(); i++) {
//                T entity = (T) entityFactory.createEntity(al[i].toString());
//                out.writeObject((Pacient) entity);
//            }
//            out.close();
//            fileOut.close();
//            System.out.println("Serialized data is saved in /tmp/employee.bin");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
//    private void readFromFile(String fileName)
//    {
//
////        //deserializare
//        //Pacient p = new Pacient(17,"fghgf","gfghff",56);
//     //   T p=(T) entityFactory.createEntity("17,dfsdf,trtrtr,45");
//        T p,p1;
//        try {
//            FileInputStream fileIn = new FileInputStream(fileName);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            p = (T) in.readObject();
//            p1 = (T) in.readObject();
//            //System.out.println(p);
//            add(p);
//            add(p1);
//            in.close();
//            fileIn.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//            //return;
//        } catch (ClassNotFoundException c) {
//            System.out.println("Employee class not found");
//            c.printStackTrace();
//            //return;
//        }
//
//
//    }
//
//
//
//
//    }


//package Repository;
//
//import Domain.Entity;
//import Exceptions.DuplicateEntityExceptions;
//import Exceptions.ObjectNotFoundException;
//
//import java.io.*;
//import java.util.List;
//
//public class BinaryFileRepository<T extends Entity> extends Repository<T>{
//    private String filename;
//
//    public BinaryFileRepository(String filename) throws IOException, ClassNotFoundException {
//        this.filename=filename;
//        loadFile();
//    }
//
//    @Override
//    public void add_pacient(T o) throws DuplicateEntityExceptions {
//        super.add_pacient(o);
//        // saveFile se executa doar daca super.add() nu a aruncat exceptie
//        try {
//            saveFile();
//        } catch (IOException e) {
//            throw new DuplicateEntityExceptions("Error saving file " + e.getMessage());
//        }
//    }
//    public void remove(int id) throws ObjectNotFoundException {
//        super.remove(id);
//        try {
//            saveFile();
//        } catch (Exception e) {
//            throw new ObjectNotFoundException("Error saving object" + e.getMessage());
//        }
//    }
//
//    public void update(T entity) throws ObjectNotFoundException {
//        super.update(entity);
//        try {
//            saveFile();
//        } catch (Exception e) {
//            throw new ObjectNotFoundException("Error saving object" + e.getMessage());
//        }
//    }
//
//    public T find(int id)
//    {
//        return super.find(id);
//    }
//    private void loadFile() throws IOException, ClassNotFoundException {
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
//            List<T> entitiesFromFile = (List<T>) objectInputStream.readObject();
//            entities.addAll(entitiesFromFile);
//        } catch (IOException | ClassNotFoundException e) {
//            // Ignorați în cazul în care fișierul nu există sau conține date corupte
//        }
//    }
//    private void saveFile() throws IOException {
//        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
//            objectOutputStream.writeObject(entities);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
