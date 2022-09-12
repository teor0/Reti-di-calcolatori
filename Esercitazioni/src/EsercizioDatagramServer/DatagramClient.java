package EsercizioDatagramServer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;

public class DatagramClient {
    
    public static void main(String [] args){
        try {
            DatagramSocket s= new DatagramSocket();
            String host= "localhost";
            InetAddress ip=InetAddress.getByName(host);
            byte[] buffer=new byte[256];
            String hello= "Hello server.";
            buffer=hello.getBytes();
            DatagramPacket p=new DatagramPacket(buffer, buffer.length, ip, 4340);
            System.out.println("Mando il pacchetto al server...");
            s.send(p);
            buffer=new byte[256];
            p=new DatagramPacket(buffer,buffer.length);
            System.out.println("Attendo la risposta del server...");
            s.receive(p);
            String response= new String(p.getData());
            System.out.println("Response "+ response);
            s.close();
        } catch (IOException e) {
            System.out.println("Errore"+ e);
        }



    }

}
