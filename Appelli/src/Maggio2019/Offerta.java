package Maggio2019;

import java.io.Serializable;

public class Offerta  implements Serializable{

    private Prodotto p;
    private int c;
    private int cifra;

    public Offerta(Prodotto p, int c, int cifra){
        this.p=p;
        this.c=c;
        this.cifra=cifra;
    }

    public int getC() {
        return c;
    }

    public int getCifra() {
        return cifra;
    }

    public Prodotto getP() {
        return p;
    }

    public void setCifra(int cifra) {
        this.cifra = cifra;
    }

    public void setP(Prodotto p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Offerta per: "+ p+ "\n"+
        "Client: "+c+ " prezzo= "+cifra;
    }

}
