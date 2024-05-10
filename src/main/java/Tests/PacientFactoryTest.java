package Tests;

import Domain.IEntityFactory;
import Domain.Pacient;
import Domain.PacientFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PacientFactoryTest {
    @Test
    public void testCreateEntity() {
        IEntityFactory<Pacient> pFactory = new PacientFactory();
        String inputLine = "1,A,B,21";
        Pacient p = pFactory.createEntity(inputLine);
        assertNotNull(p);
        assertEquals(1, p.getId());
        assertEquals("A", p.getNume());
        assertEquals("B", p.getPrenume());
        assertEquals(21,p.getVarsta());
    }
}
