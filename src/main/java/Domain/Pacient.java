package Domain;

import java.io.Serializable;

public class Pacient extends Entity implements Serializable {
    private static final long serialVersionUID = 1000L;
    private String nume;
    private String prenume;
    private int varsta;



    public Pacient(int id, String nume, String prenume, int varsta) {
        super(id);
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    //getteri
    public int getId() {
        return this.id;
    }

    public String getNume() {
        return this.nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public int getVarsta() {
        return this.varsta;
    }

    //setteri
    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Id: " + id + " nume: " + nume + " prenume: " + prenume + " varsta: " + varsta;
    }
}
