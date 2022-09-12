package EsercizioDatagramServer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.*;
public class DatagramServer {
    
    public static void main(String[] args){
        try {
            DatagramSocket s=new DatagramSocket(4340);
            byte [] buffer=new byte[256];
            int i=1;
            while(true){
                DatagramPacket p=new DatagramPacket(buffer, buffer.length);
                System.out.println("Attendo di ricevere il pacchetto dal client");
                s.receive(p); //riceve il pacchetto dal client
                String content=new String(p.getData());
                System.out.println(content);
                System.out.println("Compongo il pacchetto...");
                String lsp=i+ " Server LSDB";
                buffer=lsp.getBytes();
                InetAddress ip=p.getAddress();
                int port=p.getPort();
                System.out.println("ip: "+ip);
                System.out.println("porta: "+port);
                p=new DatagramPacket(buffer, buffer.length,ip,port);
                System.out.println("Rimando il pacchetto");
                i++;
                s.send(p);
            }
           // s.close();
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }
    }
}
