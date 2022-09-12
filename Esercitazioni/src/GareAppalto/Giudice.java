package GareAppalto;

import java.net.*;
import java.io.*;

public class Giudice {
    
    private static int portaEnte=2000;
    private static int portaOfferte=4000;
    private static int msPort=3000;
    private final static String g="230.0.0.1";
    private final static int n=10;
    


    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(portaEnte);
            Socket s=ss.accept();
            InetAddress group=InetAddress.getByName(g);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Richiesta r=(Richiesta) ois.readObject();
            System.out.println(r);
            inviaRichiesta(r,group);
            Offerta winner=riceviOfferte();
            System.out.println("Vincitore: "+ winner);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(winner);
            esitoGara(group,winner);

        } catch (Exception e) {
        }
    }


    public static void inviaRichiesta(Richiesta r, InetAddress add){
        try {
            MulticastSocket ms=new MulticastSocket(msPort);
            byte [] b=new byte[256];
            String s=r.getDesc() + "-"+r.getImpMax();
            b=s.getBytes();
            DatagramPacket p=new DatagramPacket(b, b.length, add, msPort);
            ms.send(p);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Offerta riceviOfferte(){
        Offerta winner=null;
        try {
            ServerSocket ss=new ServerSocket(portaOfferte);
            //ss.setSoTimeout(60000); per implementare il timer per l'offerte
            for(int i=0; i<n; i++){
                Socket s=ss.accept();
                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                Offerta o=(Offerta) ois.readObject();
                if(winner==null)
                    winner=o;
                else if(winner.getImporto()> o.getImporto())
                    winner=o;
                s.close();
                ois.close();
            }
        } catch (Exception e) {
            System.err.println("Tempo scaduto "+e); 
        }
        return winner;
    }

    public static void esitoGara(InetAddress add, Offerta winner){
        try {
            MulticastSocket ms=new MulticastSocket(msPort);
            String esito=winner.getId()+"-"+winner.getImporto();
            byte[] b=esito.getBytes();
            DatagramPacket p=new DatagramPacket(b, b.length, add, msPort);
            ms.send(p);
        } catch (Exception e) {
            System.err.println(e);
        }
    }


}
