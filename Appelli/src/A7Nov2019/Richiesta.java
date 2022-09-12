package A7Nov2019;

import java.io.Serializable;

public class Richiesta  implements Serializable{
    
    private String prodotto;
    private int quantita;
    private int prezzoUnitario;

    public int getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public String getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setPrezzoUnitario(int prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public void setProdotto(String prodotto) {
        this.prodotto = prodotto;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

}
