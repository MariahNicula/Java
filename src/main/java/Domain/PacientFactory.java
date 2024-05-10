package Domain;

public class PacientFactory implements IEntityFactory<Pacient>{
    @Override
    public Pacient createEntity(String line)
    {
        int id = Integer.parseInt(line.split(",")[0]);
        String nume = line.split(",")[1];
        String prenume = line.split(",")[2];
        int varsta = Integer.parseInt(line.split(",")[3]);

        return new Pacient(id,nume,prenume,varsta);
    }

    @Override
    public String toString(Pacient object){
        return object.getId()+","+object.getNume()+","+object.getPrenume()+","+object.getVarsta();
    }
}