package GareAppalto;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;

public class Partecipante extends Thread{

    private final String add="127.0.0.1";
    private int id;

    public Partecipante(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println("Sono il partecipante: "+id);
        try {
            MulticastSocket ms=new MulticastSocket(3000);
            InetAddress group=InetAddress.getByName("230.0.0.1");
            ms.joinGroup(group);
            byte[] b=new byte[256];
            DatagramPacket p=new DatagramPacket(b, b.length);
            ms.receive(p);
            String richiesta=new String(p.getData());
            String[] partiRichiesta=richiesta.split("-");
            String descrizione=partiRichiesta[0];
            int importoMax=Integer.parseInt(partiRichiesta[1].trim());

            Socket s=new Socket(add,4000);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            Offerta o=new Offerta(this.id, new Random().nextInt(500000));
            oos.writeObject(o);
            s.close();
            System.out.println("Invio offerta");
            ms.receive(p);
            String[] esito=new String(p.getData()).split("-");
            System.out.println("ID: "+esito[0]);
            System.out.println("Importo vincitore "+esito[1].trim());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++){
            Partecipante p=new Partecipante(i);
            p.start();
        }
    }
    
}
