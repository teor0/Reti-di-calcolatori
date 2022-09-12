package GareAppaltoPlus;
import java.io.*;
import java.net.*;
import java.util.*;

public class Partecipante extends Thread{

    private final String add="127.0.0.1";
    private int id;

    public Partecipante(int id){
        this.id=id;
    }

    private void gestisciRichiesta(String[] partiRichiesta) throws IOException, InterruptedException{
        int idGara=Integer.parseInt(partiRichiesta[1]);
        int importoMax=Integer.parseInt(partiRichiesta[3].trim());
        sleep(new Random().nextInt(60000));
        Socket s=new Socket(add,4000);
        ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
        Offerta o=new Offerta(id, idGara, importoMax);
        out.writeObject(o);
        System.out.println("Offerta inviata: "+ o);
        s.close();
    }

    @Override
    public void run() {
        System.out.println("Sono il partecipante: "+id);
        try {
            MulticastSocket ms=new MulticastSocket(3000);
            InetAddress group=InetAddress.getByName("230.0.0.1");
            ms.joinGroup(group);
            while(true){ //il while serve per consentire di partecipare a più gare
                byte[] b=new byte[256];
                DatagramPacket p=new DatagramPacket(b, b.length);
                ms.receive(p);
                String richiesta=new String(p.getData());
                String[] partiRichiesta=richiesta.split("-");
                //adesso la richiesta contiene la parola richiesta o esito all'inizio, questo perchè
                //il pacchetto multicast può contenere anche l'esito della gara
                if(partiRichiesta[0].equals("RICHIESTA")) 
                    gestisciRichiesta(partiRichiesta);
                else
                    System.out.println("Esito :" + richiesta);
            }
            
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
