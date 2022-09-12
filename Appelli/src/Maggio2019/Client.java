package Maggio2019;

import java.net.*;
import java.util.Random;
import java.io.*;

public class Client implements Serializable{

    private Socket s;
    private static final int serverPort=3000;
    private static final int multiPort=4000;
    private static final String serverName="aste.unical.it";
    private InetAddress add;
    private int ID;

    public Client(){
        try {
            ID=new Random().nextInt(0,100);
            add=InetAddress.getByName("localhost"); //sarebbe aste.unical.it
            s=new Socket(add,serverPort);
        } catch (Exception e) {
            System.out.println("errore client"+e);
        }
    }

    public int getID() {
        return ID;
    }

    public boolean offri(Prodotto p){
        try {
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            Offerta of=new Offerta(p, this.getID(), new Random().nextInt(p.getPrezzoMin(),p.getPrezzoMin()*2));
            out.writeObject(of);
            System.out.println("Mando offerta..."+ of);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            String msg=(String) ois.readObject();
            System.out.println(msg);
            if(msg.equals("OK"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ottieniRisultato(){
        try {
            MulticastSocket ms=new MulticastSocket(multiPort);
            ms.joinGroup(InetAddress.getByName("230.0.0.1"));
            byte[] buf=new byte[512];
            DatagramPacket p=new DatagramPacket(buf, buf.length);
            ms.receive(p);
            String esito=new String(p.getData());
            System.out.println(esito);
            ms.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Client c=new Client();
        if(c.offri(new Prodotto(4, 30, 5000)))
            c.ottieniRisultato();
        System.out.println("Fine "+ c.getID());
    }
    
}
