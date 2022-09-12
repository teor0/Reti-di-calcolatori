package GareAppalto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Ente implements Serializable {

    public static void main(String[] args) {
        String add="127.0.0.1";
        int port=2000;
        try {
            Socket s=new Socket(add,port);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Richiesta("Stadio", 500000));
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Offerta bestOfferta=(Offerta) ois.readObject();
            oos.close();
            ois.close();
            System.out.println("Ricevuta offerta migliore: "+bestOfferta);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
}
