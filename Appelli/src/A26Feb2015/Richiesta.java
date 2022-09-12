package A26Feb2015;

import java.io.*;

public class Richiesta implements Serializable{

    private String hostnameC;
    private int portaClient;
    private int A;
    private int B;


    public Richiesta(String hostnameC, int portaClient, int A, int B){
        this.A=A;
        this.B=B;
        this.hostnameC=hostnameC;
        this.portaClient=portaClient;
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public String getHostnameC() {
        return hostnameC;
    }
    public int getPortaClient() {
        return portaClient;
    }

    public void setA(int a) {
        A = a;
    }

    public void setB(int b) {
        B = b;
    }

    public void setHostnameC(String hostnameC) {
        this.hostnameC = hostnameC;
    }

    public void setPortaClient(int portaClient) {
        this.portaClient = portaClient;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof Richiesta))
            return false;
        Richiesta r=(Richiesta) obj;
        return this.getHostnameC().equals(r.getHostnameC()) && this.getPortaClient()==r.getPortaClient();
    }
    
}
