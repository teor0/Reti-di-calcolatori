import java.io.Serializable;


import java.io.*;

public class Volo implements Serializable {
    
    private Orario o;
    private String idVolo;
    private Data d;
    private String citta;

    public Volo(String idVolo, String citta, Data d, Orario o){
        this.d=d;
        this.citta=citta;
        this.o=o;
        this.idVolo=idVolo;
    }

    public String getCitta() {
        return citta;
    }
    
    public String getIdVolo() {
        return idVolo;
    }

    public Data getData() {
        return d;
    }

    public Orario getOrario() {
        return o;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setIdVolo(String idVolo) {
        this.idVolo = idVolo;
    }

    public void setData(Data d) {
        this.d = d;
    }

    public void setOrario(Orario o) {
        this.o = o;
    }

    public boolean equals(Object o){
        if(this==o)
            return true;
        Volo v=(Volo) o;
        return this.idVolo.equals(v.idVolo);
    }

    @Override
    public int hashCode() {
        final int m=83;
        return idVolo.hashCode()*m+citta.hashCode()*m;
    }

}
