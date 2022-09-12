package A29Mar2022;

import java.net.*;
import java.io.*;
import java.util.*;


public class Banco {
    
    private static final int tcpPort=3000;
    private static final int multiPort=4000;
    private RegistroLotterie registro=new RegistroLotterie();

    public void avvioLotteria(Lotteria l){
        registro.addLotteria(l);
        new HandlerTimeout(l.getNome(),registro).start();
    }

    public void riceviRichieste(){
        new HandlerRichieste(registro).start();
    }

    public static void main(String[] args) {
        Banco b=new Banco();
        b.avvioLotteria(new Lotteria("befana"));
        //b.avvioLotteria(new Lotteria("italia"));
        b.riceviRichieste();
    }


    class HandlerRichieste extends Thread{

        private RegistroLotterie rl;
        private ServerSocket ss;

        public HandlerRichieste(RegistroLotterie rl){
            this.rl=rl;
        }

        @Override
        public void run() {
            try {
                ss=new ServerSocket(tcpPort);
                while(true){
                    Socket s=ss.accept();
                    System.out.println("gestisco la richiesta");
                    ObjectInputStream in =new ObjectInputStream(s.getInputStream());
                    Richiesta r=(Richiesta) in.readObject();
                    LinkedList<Biglietto> stamp=rl.getBigliettiDisponibili(r.getLotteria());
                    ListIterator<Biglietto> lit=stamp.listIterator();
                    System.out.println("Stampo biglietti liberi");
                    while(lit.hasNext()){
                        System.out.println(lit.next());
                    }
                    ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(rl.acquistaBiglietti(r.getLotteria(), r.getnBiglietti()));
                    System.out.println("Mando i biglietti... ");
                    s.close();
                }
            } catch (Exception e) {
                System.out.println("errore hr "+e);
            }
        }

    }


    class HandlerTimeout extends Thread{

        private String lotteria;
        private RegistroLotterie rl;
        private MulticastSocket ms;
        private InetAddress add;

        public HandlerTimeout(String lotteria, RegistroLotterie rl){
            this.lotteria=lotteria;
            this.rl=rl;
            try {
                add=InetAddress.getByName("230.0.0.1");
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        @Override
        public void run() {
            try {
                sleep(20*1000);
                rl.chiudiLotteria(lotteria);
                System.out.println("fine "+lotteria);
                esito();
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        private void esito(){
            try {
                ms=new MulticastSocket(multiPort);
                Biglietto winner=rl.getVincitore(lotteria);
                String msg=winner.getNomeLotteria()+" "+winner.getNum();
                byte[] b=msg.getBytes();
                DatagramPacket p=new DatagramPacket(b, b.length, add, multiPort);
                ms.send(p);
                ms.close();
            } catch (Exception e) {
                System.err.println(e);
            }
        }


    }


}
