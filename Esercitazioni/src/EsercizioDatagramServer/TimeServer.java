package EsercizioDatagramServer;

import java.net.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

public class TimeServer{
    
    public static void main(String[] args) {
        try {
            DatagramSocket ss= new DatagramSocket(5570);
            byte [] buffer=new byte[256];
            DatagramPacket p=new DatagramPacket(buffer,buffer.length);
            ss.receive(p);
            InetAddress ip=p.getAddress();
            int port=p.getPort();
            String geozone= new String(p.getData());
            System.out.println(geozone);
            TimeZone tz=TimeZone.getTimeZone(geozone);
            SimpleDateFormat sd= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            Date date= new Date();
            sd.setTimeZone(tz);
            String response=sd.format(date);
            buffer=response.getBytes();
            p=new DatagramPacket(buffer, buffer.length,ip,port);
            ss.send(p);
            System.out.println("Fine");
            ss.close();
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }

    }
}