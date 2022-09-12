package GareAppaltoPlus;

import java.io.*;

public class Richiesta implements Serializable {

    private int idEnte;
    private int impMax;
    private String desc;

    public Richiesta(int idEnte, String d, int importo){
        this.idEnte=idEnte;
        this.desc=d;
        this.impMax=importo;
    }

    public int getIdEnte() {
        return idEnte;
    }

    public int getImpMax() {
        return impMax;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Richiesta:{"+ 
            "id ente= "+ idEnte+ "\\"+
            "Descrizione= "+ desc+ "\\"+ 
            "Importo massimo "+ impMax+ 
            "}";
    }
    
}
