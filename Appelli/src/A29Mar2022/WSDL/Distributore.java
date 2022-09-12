package A29Mar2022.WSDL;

import java.io.Serializable;

public class Distributore implements Serializable{

    private String partitaIva;
    private String regione;
    private String ragioneSociale;
    private double prezzoDiesel;
    private double prezzoBenzina;

    public Distributore(String partitaIva, String regione, String ragioneSociale, double prezzoDiesel, double prezzoBenzina){
        this.partitaIva=partitaIva;
        this.prezzoBenzina=prezzoBenzina;
        this.prezzoDiesel=prezzoDiesel;
        this.regione=regione;
        this.ragioneSociale=ragioneSociale;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public double getPrezzoBenzina() {
        return prezzoBenzina;
    }

    public double getPrezzoDiesel() {
        return prezzoDiesel;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public String getRegione() {
        return regione;
    }

    public void setPrezzoDiesel(double prezzoDiesel) {
        this.prezzoDiesel = prezzoDiesel;
    }

    public void setPrezzoBenzina(double prezzoBenzina) {
        this.prezzoBenzina = prezzoBenzina;
    }
    
}
