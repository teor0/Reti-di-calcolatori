package A7Nov2019;

import java.io.Serializable;

public class Risposta implements Serializable{

    private int idNegozio;
    private int prezzoAcquisto;

    public Risposta(int idNegozio, int prezzoAcquisto){
        this.idNegozio=idNegozio;
        this.prezzoAcquisto=prezzoAcquisto;
    }

    public int getIdNegozio() {
        return idNegozio;
    }

    public int getPrezzoAcquisto() {
        return prezzoAcquisto;
    }
    
}
