package Multicast;

import java.net.*;
import java.io.*;

public class MulticastClient {

    public static void main(String[] args) {
        int port=2200;
        try {
            MulticastSocket s= new MulticastSocket(port);
            InetAddress group=InetAddress.getByName("230.0.0.1");
            SocketAddress sa=new InetSocketAddress(group, port);
            NetworkInterface ni=NetworkInterface.getByName("wlo1");
            s.joinGroup(sa, ni);
            byte[] buffer=new byte[1024];
            System.out.println("Joined the group: "+group.toString());
            DatagramPacket p=new DatagramPacket(buffer, buffer.length);
            s.receive(p);
            String time=new String(p.getData());
            System.out.println("Local time: "+time);
            s.leaveGroup(group);
            s.close();
        } catch (Exception e) {
            System.out.println("Errore" +e);
        }
    }
    
}
