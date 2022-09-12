package WSDLCorso;

import java.io.Serializable;

public class Corso implements Serializable{

    private String docente;
    private String nomeCorso;
    private int numeroCrediti;
    private int oreEsercitazione;
    private int oreLezione;
    private String programma;

    public Corso(String nomeCorso, String docente, String programma, int numeroCrediti, int oreLezione, int oreEsercitazione){
        this.nomeCorso=nomeCorso;
        this.docente=docente;
        this.programma=programma;
        this.numeroCrediti=numeroCrediti;
        this.oreLezione=oreLezione;
        this.oreEsercitazione=oreEsercitazione;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public String getDocente() {
        return docente;
    }

    public int getNumeroCrediti() {
        return numeroCrediti;
    }

    public int getOreLezione() {
        return oreLezione;
    }

    public int getOreEsercitazione() {
        return oreEsercitazione;
    }

    public String getProgramma() {
        return programma;
    }

    public Corso(String nomeCorso){
        this.nomeCorso=nomeCorso;
    }

    @Override
    public String toString(){
        return nomeCorso + " "+ numeroCrediti+ " CFU";
    }
    
}
