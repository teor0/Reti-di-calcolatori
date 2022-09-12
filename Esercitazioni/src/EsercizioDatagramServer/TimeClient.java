package EsercizioDatagramServer;
import java.net.*;
import java.util.*;
import java.io.*;
public class TimeClient {

    public static void main(String[] args) {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Inserisci la zona geografica di cui vuoi conoscere l'orario");
            String timezone= sc.nextLine();
            sc.close();
            DatagramSocket s=new DatagramSocket();
            byte[] buffer= new byte[256];
            buffer=timezone.getBytes();
            String host="localhost";
            InetAddress ip=InetAddress.getByName(host);
            DatagramPacket p=new DatagramPacket(buffer, buffer.length,ip,5570);
            s.send(p);
            buffer=new byte[512];
            p=new DatagramPacket(buffer, buffer.length);
            s.receive(p);
            String orario=new String(p.getData());
            System.out.println(orario);
            s.close();
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }
    }
    
}
