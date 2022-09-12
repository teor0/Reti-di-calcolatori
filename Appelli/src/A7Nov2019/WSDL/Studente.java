package A7Nov2019.WSDL;

import java.io.Serializable;

public class Studente implements Serializable{
    
    private String matricola;
    private String nome;
    private String cognome;

    public Studente(String matricola, String nome, String cognome){
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricola() {
        return matricola;
    }

    @Override
    public String toString() {
        return "Matricola "+ matricola + " Nome "+nome+ " Cognome "+cognome;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        else if(this==null || !(o instanceof Studente))
            return false;
        Studente s=(Studente)o;
        return this.getMatricola().equals(s.getMatricola());        
    }

}
