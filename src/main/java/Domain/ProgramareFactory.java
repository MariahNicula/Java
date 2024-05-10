package Domain;

public class ProgramareFactory implements IEntityFactory<Programare>{
    @Override
    public Programare createEntity(String line)
    {

        int id = Integer.parseInt(line.split(",")[0]);
        int id_pacient = Integer.parseInt(line.split(",")[1]);
        String data = line.split(",")[2];
        String ora = line.split(",")[3];
        String scop = line.split(",")[4];

        return new Programare(id,id_pacient,data,ora,scop);

    }

    @Override
    public String toString(Programare object){
        return object.getId()+","+object.getPacient()+","+object.getData()+","+object.getOra()+","+object.getScop();
    }

}
