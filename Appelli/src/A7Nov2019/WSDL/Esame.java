package A7Nov2019.WSDL;

import java.io.Serializable;
import java.util.*;

public class Esame implements Serializable{
    
    private int id;
    private HashMap<String, Integer> voti=new HashMap<>();

    public Esame(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public Set<String> getStudenti(){
        return voti.keySet();
    }

    public HashMap<String, Integer> getVoti(){
        return voti;
    }

    public void addVoto(String matricola, int voto){
        this.voti.put(matricola, voto);
    }

    @Override
    public String toString() {
        return id+" "+Arrays.toString(this.getStudenti().toArray());
    }

    


}
