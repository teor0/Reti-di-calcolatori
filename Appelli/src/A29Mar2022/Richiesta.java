package A29Mar2022;

import java.io.Serializable;

public class Richiesta implements Serializable{

    private int nBiglietti;
    private String lotteria;

    public Richiesta(int nBiglietti, String lotteria){
        this.nBiglietti=nBiglietti;
        this.lotteria=lotteria;
    }

    public String getLotteria() {
        return lotteria;
    }

    public int getnBiglietti() {
        return nBiglietti;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(this==null || !(obj instanceof Richiesta))
            return false;
        Richiesta r=(Richiesta)obj;
        return this.lotteria.equals(r.lotteria) && this.nBiglietti==r.getnBiglietti();
    }
    
}
