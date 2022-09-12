package A29Mar2022;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client {

    private static final int serverPort=3000;
    private static final int multiPort=4000;
    private Richiesta r;
    private int id;
    private Socket s;
    private InetAddress add;

    public Client(Richiesta r){
        this.r=r;
        id=new Random().nextInt(100);
        try {
            add=InetAddress.getByName("localhost");//sarebbe lotterie.unical.it
            s=new Socket(add,serverPort);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public int getId() {
        return id;
    }

    public Richiesta getRichiesta() {
        return r;
    }

    public void acquista(){
        try {
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(r);
            out.flush();
            System.out.println("Mando la richiesta");
            ObjectInputStream in=new ObjectInputStream(s.getInputStream());
            List<Biglietto> biglietti=(List<Biglietto>) in.readObject();
            //esula dalla traccia
            ListIterator<Biglietto> lit=biglietti.listIterator();
            while(lit.hasNext()){
                Biglietto b=lit.next();
                System.out.println(b);
            }
        } catch (Exception e) {
            System.out.println("errore client"+e);
        }
    }

    public void esitoLotteria(){
        try {
            MulticastSocket ms=new MulticastSocket(multiPort);
            ms.joinGroup(InetAddress.getByName("230.0.0.1"));
            byte [] b=new byte[512];
            DatagramPacket p=new DatagramPacket(b, b.length);
            ms.receive(p);
            String esito=new String(p.getData());
            System.out.println(esito);
            ms.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Client c=new Client(new Richiesta(3, "befana"));
        c.acquista();
        c.esitoLotteria();
    }
   




    
}
