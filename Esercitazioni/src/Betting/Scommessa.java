package Betting;
import java.net.*;
public class Scommessa {

    private int cavallo;
    private int nScommessa;
    private long puntata;
    private InetAddress scommettitore;
    private static int nextID=0;


    public Scommessa(int cavallo, long puntata, InetAddress ip){
        nScommessa=nextID++;
        this.cavallo=cavallo;
        this.puntata=puntata;
        scommettitore=ip;
    }

    public int getID(){
        return nScommessa;
    }

    public int getCavallo(){
        return cavallo;
    }

    public long getPuntata(){
        return puntata;
    }

    public InetAddress getScommettitore(){
        return scommettitore;
    }

    public boolean equals(Object o){
        if(!(o instanceof Scommessa))
            return false;
        Scommessa s=(Scommessa) o;
        return cavallo==s.cavallo;
    }

}
