package EsercizioStudente;
import java.io.*;

public class Studente implements Serializable {

    private String nome, cognome, Cdl;
    private int matricola;

    public Studente(int matricola, String nome, String cognome, String Cdl){
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.Cdl=Cdl;
    }

    public int getMatricola(){
        return this.matricola;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getCdl(){
        return Cdl;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o==null || !( o instanceof Studente))
            return false;
        if(o==this)
            return true;
        Studente s=(Studente)o;
        if(!s.getNome().equals(this.getNome()) || !s.getCognome().equals(this.getCognome())
            || !s.getCdl().equals(this.getCdl()) || s.getMatricola()!=this.getMatricola())
            return false;
        return true;
    }

}
