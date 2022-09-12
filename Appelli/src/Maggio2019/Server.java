package Maggio2019;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
    
    private static final int tPort=3000;
    private static final int mPort=4000;
    private RegistroAste registro=new RegistroAste();

    public void avvia(){
        memorizza(new Prodotto(1, 30, 5000));
        memorizza(new Prodotto(4, 30, 1000));
        new HandlerOfferte(registro).start();
    }

    public void memorizza(Prodotto p){
        int idASta=registro.addAsta(p);
        new HandlerTimeout(idASta,registro).start();
    }

    public static void main(String[] args) {
        Server s=new Server();
        s.avvia();
    }


    class HandlerOfferte extends Thread{

        private ServerSocket ss;
        private RegistroAste r;

        public HandlerOfferte(RegistroAste r){
            this.r=r;
        }

        @Override
        public void run() {
            try {
                ss=new ServerSocket(tPort);
                while(true){
                    Socket s=ss.accept();
                    System.out.println("Attendo offerte");
                    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                    Offerta o=(Offerta) ois.readObject();
                    ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                    //ricevo offerta ed elaboro
                    int esitoOfferta=registro.addOfferta(o);
                    if(esitoOfferta==1){
                        out.writeObject("OK");
                    }
                    else if(esitoOfferta==2){
                        out.writeObject("TROPPO BASSA");
                    }
                    else
                        out.writeObject("SCADUTA");
                    s.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class HandlerTimeout extends Thread{

        private RegistroAste r;
        private int idAsta;

        public HandlerTimeout(int idAsta,RegistroAste r){
            this.r=r;
            this.idAsta=idAsta;
        }

        @Override
        public void run() {
            try {
                sleep(r.getProdotto(idAsta).getDurata()*1000);
                r.chiudiAsta(idAsta);
                esitoAsta(idAsta);
                
            } catch (Exception e) {
                System.out.println("errore"+e);
            }
        }

        private void esitoAsta(int idAsta){
            try {
                MulticastSocket ms=new MulticastSocket(4000);
                Offerta winner=r.getOffertaMigliore(idAsta);
                if(winner==null)
                    winner=new Offerta(new Prodotto(-1), -1, -1);
                String msg="ESITO "+ idAsta+ " "+ winner;
                byte[] b=msg.getBytes();
                DatagramPacket p=new DatagramPacket(b, b.length, InetAddress.getByName("230.0.0.1"),4000);
                ms.send(p);
                ms.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
