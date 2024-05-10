package Tests;

import Domain.Pacient;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.ObjectNotFoundException;
import Repository.IRepository;
import Repository.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


public class MemoryRepoTest {
    @Test
    public void testAdd() throws DuplicateEntityExceptions {
        IRepository<Pacient> data = new Repository<Pacient>();
        data.add_pacient(new Pacient(1, "A", "B", 21));
        data.add_pacient(new Pacient(2, "A", "B", 22));
        assertEquals(2, data.getAll().size());
    }

    @Test
    public void testRemove() throws DuplicateEntityExceptions, IOException, ObjectNotFoundException {
        IRepository<Pacient> data = new Repository<Pacient>();
        Pacient p1 = new Pacient(1, "John", "Punto", 20);
        data.add_pacient(p1);
        Pacient p2 = new Pacient(2, "Aris", "B", 22);
        data.add_pacient(p2);
        data.remove(p1.getId());
        assertEquals(1, data.getAll().size());
        assertFalse(data.getAll().contains(p1));
        assertTrue(data.getAll().contains(p2));
    }

    @Test
    public void testFind() throws DuplicateEntityExceptions, ObjectNotFoundException {
        IRepository<Pacient> data = new Repository<Pacient>();
        Pacient p1 = new Pacient(1, "John", "Punto", 20);
        data.add_pacient(p1);
        Pacient found = data.find(p1.getId());
        assertNotNull(found);
        assertEquals(p1, found);
    }

    @Test
    public void testUpdate() throws DuplicateEntityExceptions, ObjectNotFoundException {
        // Arrange
        IRepository<Pacient> data = new Repository<Pacient>();
        Pacient p1 = new Pacient(1, "John", "Punto", 20);
        data.add_pacient(p1);
        Pacient updated = new Pacient(1, "Jhon", "Clio", 20);
        data.update(updated);
        assertEquals(1, data.getAll().size());
        assertFalse(data.getAll().contains(p1));
        assertTrue(data.getAll().contains(updated));
    }

    @Test
    public void testGetAll() throws DuplicateEntityExceptions {
        // Arrange
        IRepository<Pacient> data = new Repository<Pacient>();
        Pacient p1 = new Pacient(1, "John", "Punto", 20);
        data.add_pacient(p1);
        Pacient p2 = new Pacient(2, "Andrei", "Stefan", 22);
        data.add_pacient(p2);

        // Act
        Collection<Pacient> allPacienti = data.getAll();

        // Assert
        assertEquals(2, allPacienti.size());
        assertTrue(allPacienti.contains(p1));
        assertTrue(allPacienti.contains(p2));
    }

    @Test
    public void testIterator() throws DuplicateEntityExceptions {
        // Arrange
        IRepository<Pacient> data = new Repository<Pacient>();
        Pacient p1 = new Pacient(1, "John", "Punto", 20);
        data.add_pacient(p1);
        Pacient p2 = new Pacient(2, "Andrei", "Stefan", 22);
        data.add_pacient(p2);

        // Act
        Iterator<Pacient> iterator = data.iterator();

        // Assert
        assertTrue(iterator.hasNext());
        assertEquals(p1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(p2, iterator.next());
        assertFalse(iterator.hasNext());
    }

}
