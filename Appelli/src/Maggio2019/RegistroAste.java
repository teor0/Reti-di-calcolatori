package Maggio2019;

import java.util.*;

public class RegistroAste {
    
    HashMap<Integer, Asta> aste=new HashMap<>();

    public Prodotto getProdotto(int idAsta){
        return aste.get(idAsta).getProdotto();
    }

    public Offerta getOffertaMigliore(int idAsta){
        return aste.get(idAsta).getBest();
    }

    public synchronized int addAsta(Prodotto p){
        int idAsta=p.getCodice();
        this.aste.put(idAsta, new Asta(p));
        return idAsta;
    }

    public synchronized void chiudiAsta(int idAsta){
        if(this.aste.containsKey(idAsta))
            this.aste.get(idAsta).setStatus(false);
    }

    public synchronized int addOfferta(Offerta o){
        int idAsta=o.getP().getCodice();
        int ret=0;
        if(this.aste.get(idAsta).attiva()){
            if(this.aste.get(idAsta).getBest()==null || this.aste.get(idAsta).getBest().getCifra()< o.getCifra()){
                this.aste.get(idAsta).setBest(o);
                System.out.println("Offerta ricevuta aggiunta: "+o);
                ret=1;
            }
            else{
                System.out.println("Offerta ricevuta bassa");
                ret=2;
            }
        }
        else
            System.out.println("Asta scaduta");
        return ret;
    }



    class Asta {
        Prodotto p;
        Offerta best=null;
        boolean status;
    
    public Asta(Prodotto p){
        this.p=p;
        status=true;
    }

    public void setBest(Offerta best) {
        this.best = best;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Offerta getBest() {
        return best;
    }

    public Prodotto getProdotto() {
        return p;
    }

    public boolean attiva(){
        return status;
    }
    
    
    
    
    }






}
