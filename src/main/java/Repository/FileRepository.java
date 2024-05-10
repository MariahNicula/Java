package Repository;

import Domain.Entity;
import Domain.IEntityFactory;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileRepository<T extends Entity> extends Repository<T> {
    private String fileName;
    private IEntityFactory<T> entityFactory;

    public FileRepository(String fileName, IEntityFactory<T> entityFactory) throws FileNotFoundException, DuplicateEntityExceptions, IOException {
        this.fileName = fileName;
        this.entityFactory = entityFactory;
        readFromFile();
    }
    private void readFromFile() throws FileNotFoundException, DuplicateEntityExceptions {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            T entity = entityFactory.createEntity(line);
            add_pacient(entity);
        }
        scanner.close();
    }

    public void add_pacient(T o) throws DuplicateEntityExceptions {
        super.add_pacient(o);
        try {
            saveFile();
        } catch (Exception e) {
            throw new DuplicateEntityExceptions("Error saving object" + e.getMessage());
        }
    }

    public void remove(int id) throws ObjectNotFoundException,IOException {
        super.remove(id);
        try {
            saveFile();
        } catch (Exception e) {
            throw new ObjectNotFoundException("Error saving object" + e.getMessage());
        }
    }

    public void update(T entity) throws ObjectNotFoundException {
        super.update(entity);
        try {
            saveFile();
        } catch (Exception e) {
            throw new ObjectNotFoundException("Error saving object" + e.getMessage());
        }
    }

    public T find(int id)
    {
        return super.find(id);
    }
    private void saveFile()  {
        // TODO File is rewritten at each modification :'(
        try (FileWriter fw = new FileWriter(fileName)) {
            for (T object : entities) {
                fw.write(entityFactory.toString(object));
                fw.write("\r\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
