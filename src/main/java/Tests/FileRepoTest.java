package Tests;

import Domain.IEntityFactory;
import Domain.Pacient;
import Domain.PacientFactory;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;
import Repository.FileRepository;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileRepoTest {

        private static final String TEST_FILE_NAME = "test_file_repo.txt";

        IEntityFactory<Pacient> masinaFactory = new PacientFactory() {
        };
        FileRepository<Pacient> fileRepository;
        {
            try {
                fileRepository = new FileRepository<>(TEST_FILE_NAME, masinaFactory);
            } catch (DuplicateEntityExceptions e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);}}

        @Test
        public void testAdd() throws DuplicateEntityExceptions, IOException {
            Pacient pacient = new Pacient(1, "TestMarca", "TestModel",2);
            fileRepository.add_pacient(pacient);
            assertTrue(fileContainsEntity(TEST_FILE_NAME, pacient));}
        @Test
        public void testRemove() throws DuplicateEntityExceptions, IOException, ObjectNotFoundException {
            Pacient pacient2 = new Pacient(2, "TestMarca2", "TestModel2",2);
            fileRepository.add_pacient(pacient2);
            fileRepository.remove(pacient2.getId());
            assertTrue(!fileContainsEntity(TEST_FILE_NAME, pacient2));}
        @Test
        public void testUpdate() throws DuplicateEntityExceptions, IOException,ObjectNotFoundException {
            Pacient p3 = new Pacient(3, "TestMarca3", "TestModel3",1);
            Pacient updated = new Pacient(3, "UpdatedMarca", "UpdatedModel",2);
            fileRepository.add_pacient(p3);
            fileRepository.update(updated);
            assertTrue(fileContainsEntity(TEST_FILE_NAME, updated));}

        private boolean fileContainsEntity(String fileName, Pacient pacient) throws IOException {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Pacient existingMasina = masinaFactory.createEntity(line);
                if (pacient.getId() == existingMasina.getId() &&
                        pacient.getNume().equals(existingMasina.getNume()) &&
                        pacient.getPrenume().equals(existingMasina.getPrenume())) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
            return false;}
    }
