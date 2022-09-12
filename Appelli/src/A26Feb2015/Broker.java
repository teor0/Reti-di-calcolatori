package A26Feb2015;

import java.util.*;
import java.net.*;
import java.io.*;

public class Broker {

    private HashMap<String, Integer> servers;
    //contiene hostname - numeri porta udp
    private final static int tcpPort=2000;
    private ServerSocket ss;

    public Broker(){
        try {
            ss=new ServerSocket(tcpPort); //creo server per accettare le richieste
            servers=new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avvio(){
        while(true){
            try {
                Socket sClient=ss.accept();
                new HandlerRichiesta(sClient).start();
                //uso i thread per gestire connessioni multiple
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HandlerRichiesta extends Thread{

        private Socket sClient;

        public HandlerRichiesta(Socket s){
            sClient=s;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois=new ObjectInputStream(sClient.getInputStream());
                Richiesta r=(Richiesta) ois.readObject();
                String formattedRequest=r.getHostnameC()+"-"+r.getPortaClient()+"-"+r.getA()+"-"+r.getB();
                byte[] b=formattedRequest.getBytes();
                String udpServer=scegliServer();
                int udpPort=servers.get(udpServer);
                InetAddress add=InetAddress.getByName(udpServer);
                DatagramSocket ds=new DatagramSocket();
                DatagramPacket p=new DatagramPacket(b, b.length, add, udpPort);
                ds.send(p);
                ois.close();
                ds.close();
                sClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String scegliServer(){
            String ret="";
            Set<String> hosts=servers.keySet();
            int i=new Random().nextInt(hosts.size());
            int j=0;
            Iterator<String> it=hosts.iterator();
            while(j!=i){
                ret=it.next();
                j++;
            }
            return ret;
        }

    }
    
}
