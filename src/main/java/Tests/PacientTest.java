package Tests;

import Domain.Pacient;
import org.junit.jupiter.api.Test;

public class PacientTest {
    @Test
    public void testPacient(){
      Pacient p = new Pacient(10, "orice", "orice1",21);
      assert p.getId() == 10;
      assert "orice".equals(p.getNume());
      assert "orice1".equals(p.getPrenume());
      assert p.getVarsta() == 21;
    }
}
/*
package Test;

import Domain.Masina;
import org.junit.jupiter.api.Test;
public class MasinaTest {
    @Test
    public void testMasina() {
        Masina c = new Masina(10, "orice", "orice");
        assert c.getId() == 10;
        assert "orice".equals(c.getMarca());
        assert "orice".equals(c.getModel());
    }
}
 */