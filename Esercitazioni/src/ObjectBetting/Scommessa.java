package ObjectBetting;

import java.io.Serializable;
import java.net.*;
public class Scommessa implements Serializable{
    
    private int cavallo;
    private static int nextID=1;
    private InetAddress ip;
    private int nScommessa;
    private long puntata;

    public Scommessa(int cavallo, long puntata, InetAddress ip){
        nScommessa=nextID++;
        this.puntata=puntata;
        this.cavallo=cavallo;
        this.ip=ip;
    }

    public int getCavallo() {
        return cavallo;
    }

    public long getPuntata() {
        return puntata;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getID() {
        return nScommessa;
    }

    @Override
    public boolean equals(Object o){
        if(this==null || !(o instanceof Scommessa))
            return false;
        if(this==o)
            return true;
        Scommessa s=(Scommessa) o;
        return s.getCavallo()==this.cavallo;
    }


}
