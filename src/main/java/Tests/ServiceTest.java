package Tests;

import Domain.Pacient;
import Domain.Programare;
import Exceptions.DuplicateEntityExceptions;
import Exceptions.NonExistentPatientException;
import Exceptions.ObjectNotFoundException;
import Repository.Repository;
import Service.Service;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.*;

public class ServiceTest {
    private Service serv1;
    private Repository<Pacient> repo_pacient;
    private Repository<Programare> repo_programare;

    @Before
    public void setUp() {
        repo_pacient = new Repository<Pacient>();
        repo_programare = new Repository<Programare>();
        serv1 = new Service(repo_pacient, repo_programare);
    }

    @Test
    public void testAdd_pacient() throws DuplicateEntityExceptions {
        serv1.add_pacient(1, "a", "b", 12);
        assertEquals(1, serv1.getAll_pacient().size());
    }

    @Test
    public void testRemove() throws IOException, DuplicateEntityExceptions, ObjectNotFoundException {

        serv1.add_pacient(1, "a", "b", 21);
        assertEquals(1, serv1.getAll_pacient().size());
        serv1.remove_pacient(1);
        assertEquals(0, serv1.getAll_pacient().size());
    }


    @Test
    public void testUpdate_pacient() throws DuplicateEntityExceptions, ObjectNotFoundException {
        // Adăugăm un pacient inițial
        serv1.add_pacient(1, "Dacia", "Logan", 22);

        // Verificăm că pacientul a fost adăugat
        assertEquals(1, serv1.getAll_pacient().size());

        // Actualizăm detaliile pacientului
        serv1.update_pacient(1, "ORice", "Orice", 2);

        // Obținem lista de pacienți actualizată
        Collection<Pacient> updatedPacients = serv1.getAll_pacient();

        // Verificăm că detaliile au fost actualizate corect
        for (Pacient pacient : updatedPacients) {
            if (pacient.getId() == 1) {
                assertEquals("ORice", pacient.getNume());
                assertEquals("Orice", pacient.getPrenume());
                assertEquals(2, pacient.getVarsta());
            }
        }

        // Verificăm că lista conține doar un singur pacient și acela are detaliile actualizate
        assertEquals(1, updatedPacients.size());
    }


    @Test
    public void testGetAll() throws DuplicateEntityExceptions {
        Pacient entity1 = new Pacient(1, "Dacia", "Logan", 22);
        Pacient entity2 = new Pacient(2, "Dacia", "Logan", 22);
        repo_pacient.add_pacient(entity1);
        repo_pacient.add_pacient(entity2);
        //serv1.add_pacient(1, "Dacia","Logan",22);
        Collection<Pacient> allEntities = serv1.getAll_pacient();
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
    }

    @Test
    public void testAdd_programare() throws DuplicateEntityExceptions, NonExistentPatientException {
        Programare entity = new Programare(1, 1, "12/07/2022", "12", "o");
        serv1.add_pacient(1, "a", "b", 12);
        serv1.add_programare(entity.getId(), entity.getPacient(), entity.getData(), entity.getOra(), entity.getScop());
        assertEquals(1, serv1.getAll_pacient().size());
    }

    @Test
    public void testRemove_programare() throws IOException, DuplicateEntityExceptions, NonExistentPatientException, ObjectNotFoundException {
        int id = 1;
        serv1.add_pacient(1, "a", "b", 12);
        serv1.add_programare(id, 1, "12/07/2022", "12", "o");
        assertEquals(1, serv1.getAll_programare().size());

        serv1.remove_programare(id);
        assertEquals(0, serv1.getAll_programare().size());
    }

    @Test
    public void testUpdate_programare() throws DuplicateEntityExceptions, ObjectNotFoundException, NonExistentPatientException {
        // Adăugăm o programare inițială
        serv1.add_pacient(1, "a", "b", 21);
        serv1.add_programare(1, 1, "12/07/2022", "12", "o");

        // Verificăm că programarea a fost adăugată
        assertEquals(1, serv1.getAll_programare().size());

        // Actualizăm detaliile programării
        serv1.update_programare(1, 1, "15/07/2022", "15", "nou");

        // Obținem lista de programări actualizată
        Collection<Programare> updatedProgramari = serv1.getAll_programare();

        // Verificăm că detaliile au fost actualizate corect
        for (Programare programare : updatedProgramari) {
            if (programare.getId() == 1) {
                assertEquals("15/07/2022", programare.getData());
                assertEquals("15", programare.getOra());
                assertEquals("nou", programare.getScop());
            }
        }

        // Verificăm că lista conține doar o singură programare și acela are detaliile actualizate
        assertEquals(1, updatedProgramari.size());
    }

    @Test
    public void testGetAll_programare() throws DuplicateEntityExceptions {
        Programare entity1 = new Programare(1, 1, "12/07/2022", "14", "o");
        Programare entity2 = new Programare(2, 2, "14/07/2022", "18", "9");
        repo_programare.add_pacient(entity1);
        repo_programare.add_pacient(entity2);
        Collection<Programare> allEntities = serv1.getAll_programare();
        assertTrue(allEntities.contains(entity1));
        assertTrue(allEntities.contains(entity2));
    }

    @Test(expected = NonExistentPatientException.class)
    public void testUpdate_programare_NonExistentPatientException() throws DuplicateEntityExceptions, ObjectNotFoundException, NonExistentPatientException {
        // Încercăm să actualizăm o programare pentru un pacient care nu există
        serv1.update_programare(1, 999, "15/07/2022", "15", "nou");
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testUpdate_programare_ObjectNotFoundException() throws DuplicateEntityExceptions, ObjectNotFoundException, NonExistentPatientException {
        // Adăugăm un pacient și o programare
        serv1.add_pacient(1, "a", "b", 21);
        serv1.add_programare(1, 1, "12/07/2022", "12", "o");

        // Încercăm să actualizăm o programare care nu există
        serv1.update_programare(999, 1, "15/07/2022", "15", "nou");
    }
    @Test(expected = ObjectNotFoundException.class)
    public void testRemove_pacient_ObjectNotFoundException() throws IOException, DuplicateEntityExceptions, ObjectNotFoundException {
        // Încercăm să eliminăm un pacient care nu există
        serv1.remove_pacient(999);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testRemove_programare_ObjectNotFoundException() throws IOException, DuplicateEntityExceptions, NonExistentPatientException, ObjectNotFoundException {
        // Adăugăm un pacient și o programare
        serv1.add_pacient(1, "a", "b", 21);
        serv1.add_programare(1, 1, "12/07/2022", "12", "o");

        // Încercăm să eliminăm o programare care nu există
        serv1.remove_programare(999);
    }

    @Test(expected = NonExistentPatientException.class)
    public void testAdd_programare_NonExistentPatientException() throws DuplicateEntityExceptions, NonExistentPatientException {
        // Încercăm să adăugăm o programare pentru un pacient care nu există
        serv1.add_programare(1, 999, "12/07/2022", "12", "o");
    }

    @Test(expected = NonExistentPatientException.class)
    public void testAdd_programare_NonExistentPatientException2() throws DuplicateEntityExceptions, NonExistentPatientException {
        // Încercăm să adăugăm o programare pentru un pacient care nu există
        serv1.add_programare(1, 999, "12/07/2022", "12", "o");
    }
    @Test(expected = DuplicateEntityExceptions.class)
    public void testAdd_pacient_DuplicateEntityExceptions() throws DuplicateEntityExceptions {
        // Adăugăm un pacient inițial
        serv1.add_pacient(1, "a", "b", 12);

        // Încercăm să adăugăm un alt pacient cu același ID, ceea ce ar trebui să arunce o excepție
        serv1.add_pacient(1, "b", "3", 1);
    }
}
