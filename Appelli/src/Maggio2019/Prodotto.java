package Maggio2019;

import java.io.Serializable;

public class Prodotto implements Serializable{

    private int codice;
    private int durata;
    private int prezzoMin;

    public Prodotto(int codice, int durata,int prezzoMin){
        this.codice=codice;
        this.durata=durata;
        this.prezzoMin=prezzoMin;
    }

    public Prodotto(int codice){
        this.codice=codice;
    }

    public int getCodice() {
        return codice;
    }
    public int getDurata() {
        return durata;
    }

    public int getPrezzoMin() {
        return prezzoMin;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof Prodotto))
            return false;
        Prodotto p=(Prodotto) obj;
        return this.codice==p.codice;
    }

    @Override
    public String toString() {
        return "Prodotto: "+ codice;
    }
    
}
