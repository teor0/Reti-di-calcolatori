package A29Mar2022.WSDL;

import java.util.*;

public class GestoreServizi {

    private HashMap<String, HashSet<Distributore>> distributori=new HashMap<>();
    //              regione, distributori della regione

    public void addDistributore(Distributore d){
        if(!this.distributori.containsKey(d.getRegione()))
            this.distributori.put(d.getRegione(), new HashSet<>());
        this.distributori.get(d.getRegione()).add(d);
    }

    public Distributore MinPrezzoBenzina(String regione){
        HashSet<Distributore> dRegione=this.distributori.get(regione);
        Iterator<Distributore> it=dRegione.iterator();
        Distributore dMin=null;
        while(it.hasNext()){
            Distributore x=it.next();
            if(dMin==null)
                dMin=x;
            else if(dMin.getPrezzoBenzina()<x.getPrezzoBenzina())
                dMin=x;
        }
        return dMin;
    }

    public String RegioneMinMediaDiesel(){
        String ret="";
        double media=0;
        Set<String> regioni=distributori.keySet();
        for(String regione: regioni){
            if(media==0){
                media=calcoloMedia(regione);
                ret=regione;
            }
            else if(media>calcoloMedia(regione)){
                media=calcoloMedia(regione);
                ret=regione;
            }
        }
        System.out.format("Media %lf, regione %s %n",media,ret);
        return ret;
    } 

    private double calcoloMedia(String regione){
        double ret;
        int i=0;
        double sum=0;
        HashSet<Distributore> d=distributori.get(regione);
        Iterator<Distributore> it=d.iterator();
        while(it.hasNext()){
            sum+=it.next().getPrezzoDiesel();
            i++;
        }
        ret=sum/i;
        return ret;
    }
    
}
