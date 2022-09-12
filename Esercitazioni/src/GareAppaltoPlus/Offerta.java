package GareAppaltoPlus;

import java.io.Serializable;

public class Offerta implements Serializable{

    private int importo;
    private int idPartecipante;
    private int idGara;

    public Offerta(int id, int idGara,int imp){
        this.idPartecipante=id;
        this.idGara=idGara;
        this.importo=imp;
    }

    public int getImporto() {
        return importo;
    }

    public int getIdPartecipante() {
        return idPartecipante;
    }

    public int getIdGara() {
        return idGara;
    }
    
    @Override
    public String toString() {
        return "Offerta:{"+ 
            "ID= "+ idPartecipante+ "\\"+ 
            "Importo "+ importo+ "\\"+
            "ID Gara= "+ idGara+
            "}";
    }
}
