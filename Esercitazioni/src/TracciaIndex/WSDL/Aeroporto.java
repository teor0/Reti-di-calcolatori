import java.util.*;
import java.util.Map.*;
public class Aeroporto {
    
    private HashMap<Data, HashMap<String, LinkedList<Volo>>> db;
    
    public Aeroporto(){
        db=new HashMap<Data, HashMap<String, LinkedList<Volo>>>();
    }

    public String primoVolo(String citta, Data data){
        if(!db.containsKey(data) || !db.get(data).containsKey(citta))
            return "Nessun volo";
        return db.get(data).get(citta).getFirst().getIdVolo();
    }

    public Orario OrarioVolo(String idVolo, Data data){
        LinkedList<Volo> tmp;
        if(! db.containsKey(data))
            return null;
        for(Entry<String, LinkedList<Volo>> e: db.get(data).entrySet()){
            tmp=e.getValue();
            for(Volo v: tmp){
                if(v.getIdVolo().equals(idVolo))
                return v.getOrario();
            }
        }
        return new Orario(-1, -1);
    }



}
