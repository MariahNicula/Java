package Domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Programare extends Entity implements Serializable {
   private int id;
   private int id_pacient;
   private String data;
   private String ora;
   private String scop;

   public Programare()
   {
      super(0);
      this.id = 0;
      this.id_pacient=0;
      this.data=null;
      this.ora=null;
      this.scop=null;
   }

   public Programare(int id, int id_pacient, String data, String ora, String scop)
   {
      super(id);
      this.id = id;
      this.id_pacient = id_pacient;
      this.data = data;
      this.ora = ora;
      this.scop = scop;
   }

   public int getId()
   {
      return this.id;
   }

   public int getPacient()
   {
      return this.id_pacient;
   }
   public String getData()
   {
      return this.data;
   }
   public String getOra()
   {
      return this.ora;
   }
   public String getScop()
   {
      return this.scop;
   }

   public void setId(int id)
   {
      this.id=id;
   }
   public void setPacient(int id_p)
   {
      this.id_pacient=id_p;
   }
   public void setData(String data)
   {
      this.data=data;
   }
   public void setOra(String ora)
   {
      this.ora=ora;
   }
   public void setScop(String scop)
   {
      this.scop=scop;
   }



   @Override
   public String toString(){
      return "Id: "+ id + " id_pacient: " + id_pacient + " data: " + data + " ora: " + ora + " scop: " + scop;
   }
}
