package A26Feb2015;

import java.io.*;

public class Risposta implements Serializable{
    
    private int C;

    public Risposta(int C){
        this.C=C;
    }

    public int getC() {
        return C;
    }
    
    public void setC(int c) {
        C = c;
    }

}
