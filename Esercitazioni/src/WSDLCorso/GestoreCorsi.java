package WSDLCorso;

import java.util.HashMap;

public class GestoreCorsi {
    
    private HashMap<String, Corso> corsi;

    public GestoreCorsi(){
        corsi=new HashMap<>();
    }

    public void aggiungiCorso(Corso c){
        String nomeCorso=c.getNomeCorso();
        corsi.put(nomeCorso, c);
    }//aggiungiCorso

    public Corso getCorso(String nomeCorso){
        Corso ret=null;
        if(corsi.containsKey(nomeCorso))
            ret=corsi.get(nomeCorso);
        return ret;
    }//getCorso

    
}
