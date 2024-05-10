package Tests;

import Domain.Programare;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.NonExistentPatientException;
import Exceptions.ObjectNotFoundException;
import Repository.BinaryFileRepository;
import Repository.IRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryFileTest {
    private static final String fileName = "test_binary_repo.bin";
    private IRepository<Programare> binaryFileRepository;
    @BeforeEach
    void setUp() throws IOException, ClassNotFoundException {
        binaryFileRepository = new BinaryFileRepository<Programare>(fileName);
    }
    @Test
    void testAdd() throws DuplicateEntityExceptions {
        Programare entity = new Programare(1, 1, "12/07/2022","14","o");
        binaryFileRepository.add_pacient(entity);
        assertTrue(binaryFileRepository.getAll().contains(entity));
    }
    @Test
    void testRemove() throws IOException, DuplicateEntityExceptions, NonExistentPatientException, ObjectNotFoundException {
        // Arrange
        int entityId = 2;
        Programare entity = new Programare(entityId, 2, "12/07/2022","14","o");
        binaryFileRepository.add_pacient(entity);
        binaryFileRepository.remove(entityId);
        assertFalse(binaryFileRepository.getAll().contains(entity));
    }

    @Test
    void testUpdate() throws DuplicateEntityExceptions,ObjectNotFoundException {
        Programare entity = new Programare(3, 3, "12/07/2022","14","r");
        Programare entityupdate = new Programare(3, 3, "16/07/2022","19","k");
        binaryFileRepository.add_pacient(entity);
        binaryFileRepository.update(entityupdate);
        assertTrue(binaryFileRepository.getAll().contains(entityupdate));
    }

    @Test
    void testFind() throws DuplicateEntityExceptions {
        int entityId = 4;
        Programare entity = new Programare(entityId, 4, "12/07/2022","14","l");
        binaryFileRepository.add_pacient(entity);
        Programare foundEntity = binaryFileRepository.find(entityId);
        assertEquals(entity, foundEntity);
    }
}
