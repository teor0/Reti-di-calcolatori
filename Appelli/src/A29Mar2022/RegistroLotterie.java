package A29Mar2022;

import java.util.*;

public class RegistroLotterie {

    HashMap<String, Record> lotterie=new HashMap<>();

    public Biglietto getVincitore(String idLotteria){
        if(!this.lotterie.containsKey(idLotteria))
            return null;
        return this.lotterie.get(idLotteria).getWinner();
    }

    public synchronized LinkedList<Biglietto> getBigliettiDisponibili(String idLotteria){
        return this.lotterie.get(idLotteria).getLiberi();
    }

    public synchronized LinkedList<Biglietto> acquistaBiglietti(String idLotteria, int n){
        LinkedList<Biglietto> ret=new LinkedList<>();
        if(this.lotterie.get(idLotteria).attiva()){
            for(int i=0; i<n; i++)
                ret.add(this.lotterie.get(idLotteria).acquisto());
        }
        else{
            ret.add(new Biglietto());
        }
        return ret;
    }

    public synchronized String addLotteria(Lotteria l){
        String idLotteria=l.getNome();
        this.lotterie.put(idLotteria, new Record(l));
        return idLotteria;
    }

    public synchronized void chiudiLotteria(String id){
        if(this.lotterie.containsKey(id)){
            Biglietto b=this.lotterie.get(id).biglietti.get(new Random().nextInt(0,this.lotterie.get(id).biglietti.size()));
            this.lotterie.get(id).setWinner(b);
            this.lotterie.get(id).setStatus(false);
        }
    }




    class Record{

        private Lotteria l;
        private LinkedList<Biglietto> biglietti;
        private boolean status;
        private Biglietto winner=new Biglietto();

        public Record(Lotteria l){
            this.l=l;
            status=true;
            biglietti=new LinkedList<>();
            int nb=new Random().nextInt(1,10);
            for(int i=0; i<nb; i++)
                biglietti.add(new Biglietto(l.getNome()));
        }

        public LinkedList<Biglietto> getLiberi(){
            LinkedList<Biglietto> ret=new LinkedList<>();
            ListIterator<Biglietto> lit=biglietti.listIterator();
            while(lit.hasNext()){
                Biglietto b=lit.next();
                if(!b.acquistato())
                    ret.add(b);
            }
            return ret;
        }

        public Biglietto acquisto(){
            Biglietto b=new Biglietto();
            if(getLiberi().size()!=0){
               b=getLiberi().getFirst();
               b.acquista();
            }
            return b;     
        }

        public boolean attiva(){
            return status;
        }

        public Lotteria getLotteria() {
            return l;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Biglietto getWinner() {
            return winner;
        }

        public void setWinner(Biglietto winner) {
            this.winner = winner;
        }

        public LinkedList<Biglietto> getBiglietti() {
            return biglietti;
        }


    }
    
}
