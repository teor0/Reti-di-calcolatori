package A30gen2018;

import java.io.Serializable;

public class Risposta implements Serializable{

    private String idProdotto;
    private int quantita;
    private int prezzoTot;
    private String idVenditore;


    public Risposta(String idProdotto, int quantita, int prezzoTot, String idVenditore){
        this.idProdotto=idProdotto;
        this.quantita=quantita;
        this.prezzoTot=prezzoTot;
        this.idVenditore=idVenditore;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public String getIdVenditore() {
        return idVenditore;
    }

    public int getPrezzoTot() {
        return prezzoTot;
    }

    public int getQuantita() {
        return quantita;
    }
    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    public void setIdVenditore(String idVenditore) {
        this.idVenditore = idVenditore;
    }
    public void setPrezzoTot(int prezzoTot) {
        this.prezzoTot = prezzoTot;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    
}
