package GareAppalto;

import java.io.*;

public class Richiesta implements Serializable {

    private int impMax;
    private String desc;

    public Richiesta(String d, int importo){
        this.desc=d;
        this.impMax=importo;
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
            "Descrizione= "+ desc+ "\\"+ 
            "Importo massimo "+ impMax+ 
            "}";
    }
    
}
