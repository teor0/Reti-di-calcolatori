package Communicator;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Communicator {
    
    static int tcpPort;
    static int msPort=2000;

    public static void main(String[] args) {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Inserisci porta locale");
            tcpPort=sc.nextInt();
            sc.close();
            MulticastListener ml=new MulticastListener(msPort, tcpPort);
            SocketListener sl=new SocketListener(tcpPort);
            ml.start();
            sl.start();
            sendMcastDatagram();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    static void sendMcastDatagram(){
        try {
            while(true){
                MulticastSocket ms=new MulticastSocket(msPort);
                byte [] b=new byte[256];
                InetAddress group=InetAddress.getByName("230.0.0.1");
                String lPort="";
                lPort+=tcpPort;
                b=lPort.getBytes();
                DatagramPacket p=new DatagramPacket(b, b.length, group, msPort);
                System.out.println("Mando il pacchetto...");
                ms.send(p);
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            System.out.println("errore "+e);
        }
    }
}

class MulticastListener extends Thread{

    private int msPort;
    private int tcpSenderPort;

    public MulticastListener(int p1, int p2){
        msPort=p1;
        tcpSenderPort=p2;
    }

    @Override
    public void run() {
        try {
            while(true){
                MulticastSocket ml=new MulticastSocket(msPort);
                InetAddress g=InetAddress.getByName("230.0.0.1");
                ml.joinGroup(g);//aggiungo al gruppo per appunto ascoltare
                byte [] b=new byte[256];
                DatagramPacket p=new DatagramPacket(b, b.length);
                ml.receive(p);
                String risposta=new String(p.getData());
                int cmPort=Integer.parseInt(risposta);
                System.out.println("ML> porta ricevuta da "+ p.getAddress().getHostAddress()+ ": "+cmPort );
                if(cmPort!=p.getPort() || !(p.getAddress().equals(InetAddress.getLocalHost()))){//messaggio non già inviato
                    Socket s=new Socket(p.getAddress().getHostAddress(),cmPort);
                    //apro socket sulla porta ricevuta tramite multicast
                    PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
                    System.out.println("Invio la risposta a: "+ p.getAddress().getHostAddress()+":"+cmPort);
                    pw.write(tcpSenderPort);//mando la mia porta
                }
            }//while
        } catch (Exception e) {
            System.err.println(e);
        }
    }


}

class SocketListener extends Thread{

    private int myPort;
    private int senderPort; 

    public SocketListener(int port){
        this.myPort=port;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss=new ServerSocket(myPort);
            while(true){
                Socket so=ss.accept();
                InetAddress ip=so.getInetAddress();
                BufferedReader br=new BufferedReader(new InputStreamReader(so.getInputStream()));
                String line=br.readLine();
                System.out.println("Ricevo la porta del communicator");
                senderPort=Integer.parseInt(line);
                System.out.println("Risposta ottenuta da: "+ip.getHostAddress()+":"+so.getPort());
                System.out.println("Porta socket ricevuta "+senderPort);
                so.close();
                br.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
