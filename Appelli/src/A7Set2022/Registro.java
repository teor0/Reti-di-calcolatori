package A7Set2022;
import java.util.*;
public class Registro {
    
    private HashMap<String, HashMap<Integer,Integer>> reg=new HashMap<>();

    public synchronized void addPrenotazione(String categoria, int nPre, int pw){
        reg.get(categoria).put(nPre, pw); 
    }

    public synchronized boolean prenotazionePossibile(String categoria){
        return reg.get(categoria).size()<20;
    }

    public synchronized int passwordComunicazione(String categoria, int idPre){
        return reg.get(categoria).get(idPre);
    }

    public synchronized void svuotaTutto(){
        reg=new HashMap<>();
    }


    public void addCategoria(String categoria){
        reg.put(categoria, new HashMap<>());
    }

    

}
