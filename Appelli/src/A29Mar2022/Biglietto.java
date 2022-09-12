package A29Mar2022;

import java.io.Serializable;
import java.util.Random;

public class Biglietto implements Serializable{
    
    private String nomeLotteria;
    private int num;
    private boolean acquistato;

    public Biglietto(String nomeLotteria){
        this.nomeLotteria=nomeLotteria;
        num=new Random().nextInt(1,1000);
        acquistato=false;
    }

    public Biglietto(){
        num=0;
        this.nomeLotteria="";
    }

    public String getNomeLotteria() {
        return nomeLotteria;
    }

    public void acquista(){
        acquistato=true;
    }


    public boolean acquistato(){
        return acquistato;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "Biglietto: "+ nomeLotteria + " n°: "+num;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        if(this==null | !(o instanceof Biglietto))
            return false;
        Biglietto b=(Biglietto) o;
        return this.nomeLotteria.equals(b.getNomeLotteria()) && this.num==b.getNum();
    }

    

}
