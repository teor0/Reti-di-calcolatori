package A7Nov2019;

import java.net.*;
import java.io.*;

public class Client {

    private static final int serverPort=1111;
    private Richiesta r;
    private Socket s;
    private InetAddress add;

    public Client(Richiesta r){
        this.r=r;
        try {
            add=InetAddress.getByName("localhost"); //sarebbe server.dimes.it
            s=new Socket(add,serverPort);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void richiedi(){
        try {
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(r);
            System.out.println("Mando la richiesta");
            ObjectInputStream in=new ObjectInputStream(s.getInputStream());
            Risposta r=(Risposta) in.readObject();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
