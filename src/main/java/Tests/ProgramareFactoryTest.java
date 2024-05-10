package Tests;

import Domain.IEntityFactory;
import Domain.Programare;
import Domain.ProgramareFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProgramareFactoryTest {
    @Test
    public void testProgramareFactory() {
        IEntityFactory<Programare> pFactory = new ProgramareFactory();
        String inputLine = "1,123,01/01/2023,4,a";
        Programare p = pFactory.createEntity(inputLine);
        assertNotNull(p);
        assertEquals(1, p.getId());
        assertEquals(123, p.getPacient());
        assertEquals("01/01/2023", p.getData());
        assertEquals("4", p.getOra());
        assertEquals("a",p.getScop());
    }
}
