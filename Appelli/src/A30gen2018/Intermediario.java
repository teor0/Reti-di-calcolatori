package A30gen2018;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class Intermediario {

    private static final int tcpPort=2345;
    private LinkedList<Venditore> venditori;


    public Intermediario(LinkedList<Venditore> v){
        this.venditori=v;
    }

    public LinkedList<Venditore> getVenditori() {
        return venditori;
    }

    public static void main(String[] args) {
        LinkedList<Venditore> v=new LinkedList<>();
        Intermediario i=new Intermediario(v);
        try {
            ServerSocket ss=new ServerSocket(tcpPort);
            MulticastSocket ms=new MulticastSocket(6789);
            while(true){
                Socket ricRis=ss.accept();
                HandlerRichiesta hr=new HandlerRichiesta(ricRis, ms, i.getVenditori());
                hr.start();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    static class HandlerRisposta extends Thread{

        private MulticastSocket ds;
        private HandlerRichiesta hr;

        public HandlerRisposta(MulticastSocket ds, HandlerRichiesta hr){
            this.ds=ds;
            this.hr=hr;
        }

        @Override
        public void run() {
            try {
                DatagramPacket p;
                while (true) {
                    byte[] b=new byte[512];
                    p=new DatagramPacket(b, b.length);
                    ds.receive(p);
                    String msg=new String(p.getData());
                    String[] parti=msg.split(",");
                    String idPro=parti[0];
                    int quantita=Integer.parseInt(parti[1]);
                    int prezzo=Integer.parseInt(parti[2]);
                    String idVen=parti[3];
                    hr.memorizzaRisposta(new Risposta(idPro, quantita, prezzo, idVen));
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    static class HandlerRichiesta extends Thread{


        private Socket s;
        private LinkedList<Venditore> venditori;
        private MulticastSocket ms;
        private List<Risposta> risposte;

        public HandlerRichiesta(Socket s, MulticastSocket ms, LinkedList<Venditore>v){
            this.s=s;
            this.ms=ms;
            this.venditori=v;
            risposte=Collections.synchronizedList(new LinkedList<Risposta>());
        }

        public void memorizzaRisposta(Risposta r){
            this.risposte.add(r);
        }

        @Override
        public void run() {
            try {
                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                Richiesta ric=(Richiesta) ois.readObject();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                ObjectOutputStream out=new ObjectOutputStream(bos);
                out.writeObject(ric);
                out.flush();
                byte[] b=bos.toByteArray();

                for(Venditore ven: venditori){
                    MulticastSocket sv= new MulticastSocket();
                    InetAddress g=InetAddress.getByName(ven.getGroupAdd());
                    sv.joinGroup(g);
                    sv.setSoTimeout(1000*60);
                    new HandlerRisposta(sv,this).start();
                }

                Risposta rispMin=new Risposta(ric.getIdProdotto(), ric.getQuantita(), -1, "-1");
                for(Risposta risp:risposte){
                    if(risp.getPrezzoTot()<rispMin.getPrezzoTot())
                        rispMin=risp;
                }
                ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(rispMin);
                oos.flush();
                s.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
}
