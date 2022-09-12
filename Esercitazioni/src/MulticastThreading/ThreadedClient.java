package MulticastThreading;
import java.net.*;
public class ThreadedClient extends Thread {


    public static void main(String[] args) {
        new ThreadedClient().start();
    }

    @Override
    public void run(){
        try {
            int port=5500;
            String group="230.0.0.1";
            MulticastSocket ms=new MulticastSocket(port);
            InetAddress g=InetAddress.getByName(group);
            ms.joinGroup(g);
            byte[] buffer=new byte[512];
            DatagramPacket p=new DatagramPacket(buffer, buffer.length);
            ms.receive(p);
            String msg=new String(p.getData());
            System.out.println(msg);  
        } catch (Exception e) {
           System.out.println("Errore "+e);
        }
    }
    
}
