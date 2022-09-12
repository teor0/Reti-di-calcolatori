package MulticastThreading;
import java.net.*;
import java.io.*;

public class MulticastServer {
    
    public static void main(String[] args) {
        int port=5500;
        try {
            MulticastSocket ms=new MulticastSocket(5500);
            InetAddress group=InetAddress.getByName("230.0.0.1");
            byte[] buffer= new byte[512];
            String greet="Welcome";
            buffer=greet.getBytes();
            DatagramPacket p=new DatagramPacket(buffer, buffer.length, group, port);
            System.out.println("Connection started");
            while(true){
                ms.send(p);
                System.out.println("Multicasting...");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Errore "+e);
        }
    }
}
