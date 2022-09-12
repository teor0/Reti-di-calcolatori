package Multicast;

import java.net.*;
import java.util.Date;

public class MulticastServer {

    public static void main(String[] args) {
        int port=2200;
        try {
            MulticastSocket ms= new MulticastSocket(port);
            InetAddress group=InetAddress.getByName("230.0.0.0");
            byte[] buffer=new byte[1024];
            DatagramPacket p=null;
            System.out.println("Connection started");
            System.out.println("");
            while(true){
                String time= new Date().toString();
                buffer=time.getBytes();
                p=new DatagramPacket(buffer, buffer.length, group,port);   
                ms.send(p);
                System.out.println("Multicasting: "+time);
                Thread.sleep(100);
            }
       } catch (Exception e) {
        System.out.println("Errore "+e);
       }
    }
    
}
