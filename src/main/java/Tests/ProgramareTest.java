package Tests;

import Domain.Programare;
import org.testng.annotations.Test;

public class ProgramareTest {
    @Test
    public void testProgramare() {
        Programare c = new Programare(10,1, "23", "4","c");
        assert c.getId() == 10;
        assert c.getPacient() == 1;
        assert "23".equals(c.getData());
        assert "4".equals(c.getOra());
        assert "c".equals(c.getScop());
    }
}
