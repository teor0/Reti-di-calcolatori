package Maggio2019.WSDL;

import java.io.Serializable;

public class Ospedale implements Serializable{

    private String codice;
    private String citta;
    private int pazienti;
    private int postiLetto;

    public Ospedale(String codice, String citta, int pazienti, int postiLetto){
        this.codice=codice;
        this.citta=citta;
        this.pazienti=pazienti;
        this.postiLetto=postiLetto;
    }

    public Ospedale(String codice){
        this.codice=codice;
    }

    public String getCitta() {
        return citta;
    }
    public String getCodice() {
        return codice;
    }
    public int getPazienti() {
        return pazienti;
    }
    public int getPostiLetto() {
        return postiLetto;
    }

    @Override
    public String toString(){
        String s="Codice ospedale: "+ codice +"\n"+
        "Citta ospedale: "+ citta +"\n"+
        "Pazienti ospedale: "+ pazienti +"\n"+
        "Posti letto ospedale: "+ postiLetto +"\n";
        return s;
    }

    
    
}