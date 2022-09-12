package A30gen2018;

import java.io.Serializable;

public class Richiesta implements Serializable{
    
    private String idProdotto;
    private int quantita;


    public Richiesta(String idProdotto, int quantita){
        this.idProdotto=idProdotto;
        this.quantita=quantita;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        if(this==null || !(o instanceof Richiesta))
            return false;
        Richiesta r=(Richiesta) o;
        return this.idProdotto.equals(r.getIdProdotto());
    }




}
