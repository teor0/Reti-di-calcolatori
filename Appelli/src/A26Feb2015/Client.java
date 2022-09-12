package A26Feb2015;

import java.io.*;
import java.net.*;
import java.util.Random;
public class Client {

    public void riceviRisposta(Richiesta r){
        try {
            Socket s=new Socket("localhost",2000); //al posto di localhost sarebbe da mettere broker.unical.it
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(r);
            System.out.println("Mando la richiesta al broker...");
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Risposta risp=(Risposta) ois.readObject();
            System.out.println(risp.getC());
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c=new Client();
        c.riceviRisposta(new Richiesta("myhostname", 3456, new Random().nextInt(1,10000), new Random().nextInt(1,10000)));
    }
}
