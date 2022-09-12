package GareAppalto;

import java.io.Serializable;

public class Offerta implements Serializable{

    private int importo;
    private int id;

    public Offerta(int id, int imp){
        this.id=id;
        this.importo=imp;
    }

    public int getImporto() {
        return importo;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "Offerta:{"+ 
            "ID= "+ id+ "\\"+ 
            "Importo "+ importo+ 
            "}";
    }
}
