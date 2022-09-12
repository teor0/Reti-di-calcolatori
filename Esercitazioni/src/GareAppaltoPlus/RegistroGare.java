package GareAppaltoPlus;

import java.util.*;
import java.net.*;


public class RegistroGare {

    private static int COUNTER=0;
    HashMap<Integer, RecordRegistro> gare=new HashMap<>();
 
    public Richiesta getRichiesta(int idGara){
        return this.gare.get(idGara).r;
    }

    public Offerta getOffertaMigliore(int idGara){
        return this.gare.get(idGara).bestOffer;
    }

    public Socket getSocketEnte(int idGara){
        return this.gare.get(idGara).enteSocket;
    }

    public synchronized int addGara(Socket s, Richiesta r){ //synchronized perchè gestisco più gare
        int idGara=COUNTER++;
        this.gare.put(idGara, new RecordRegistro(s, r));
        return idGara;
    }

    public synchronized void chiudiGare(int idGara){
        if(this.gare.containsKey(idGara))
            this.gare.get(idGara).setStatus(false);
    }

    public synchronized void addOfferta(Offerta o){
        int idGara=o.getIdGara();
        if(this.gare.containsKey(idGara)){
            RecordRegistro gara=this.gare.get(idGara);
            if(gara.attiva()){
                Offerta winner=gara.bestOffer;
                if((winner==null && o.getImporto() <= gara.r.getImpMax()) ||winner.getImporto() > o.getImporto() || (winner.getImporto()==o.getImporto() 
                && o.getIdGara() < winner.getIdGara())){
                    gara.setBestOffer(o);
                    System.out.println("Aggiunta offerta per la gara: "+ idGara);
                }
            }
            else
                System.out.println("Rifiutata offerta per gara: "+ idGara);
        }//if
    }


    class RecordRegistro {
        
        Socket enteSocket;
        Offerta bestOffer=null;
        Richiesta r;
        boolean status;

        public RecordRegistro(Socket e, Richiesta r){
            enteSocket=e;
            this.r=r;
            this.status=true;
        }

        public void setBestOffer(Offerta o){
            bestOffer=o;
        }

        public boolean attiva(){
            return status;
        }

        public void setStatus(boolean s){
            status=s;
        }

    }


}
