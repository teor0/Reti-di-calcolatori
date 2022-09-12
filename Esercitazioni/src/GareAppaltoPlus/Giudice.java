package GareAppaltoPlus;

import java.net.*;
import java.io.*;

public class Giudice {

    private final static RegistroGare registro=new RegistroGare();

    public void avvia(){
        try {
            new HandlerRichieste(registro).start();
            new HandlerOfferte(registro).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        new Giudice().avvia();
    }


    class HandlerRichieste extends Thread{
        private static int portaEnte=2000;
        private ServerSocket ss;
        private RegistroGare registro;

        public HandlerRichieste(RegistroGare registro) throws IOException{
            this.registro=registro;
            ss=new ServerSocket(portaEnte);
        }

        @Override
        public void run() {
            try {
                while(true){
                    Socket socketRichiesta=ss.accept();
                    ObjectInputStream ois=new ObjectInputStream(socketRichiesta.getInputStream());
                    Richiesta r=(Richiesta) ois.readObject();
                    System.out.println("Ricevuta richiesta "+r);
                    int idGara=registro.addGara(socketRichiesta, r);
                    new HandlerTimeout(idGara, registro).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HandlerOfferte extends Thread{

        private static int portaOfferte=4000;
        private ServerSocket ss;
        private RegistroGare registro;

        public HandlerOfferte(RegistroGare registro){
            this.registro=registro;
        }

        @Override
        public void run() {
            try {
                ss=new ServerSocket(portaOfferte);
                while(true){
                    Socket s=ss.accept();
                    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                    Offerta o=(Offerta) ois.readObject();
                    registro.addOfferta(o);
                    s.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HandlerTimeout extends Thread{

        private final int idGara;
        private final RegistroGare registro;
        private final int groupPort=3000;
        private final String group="230.0.0.1";
        private final MulticastSocket ms;
        private final static long TIMEOUT=60000;


        public HandlerTimeout(int idGara, RegistroGare registro) throws IOException{
            this.registro=registro;
            this.idGara=idGara;
            this.ms=new MulticastSocket(groupPort);
        }

        @Override
        public void run() {
            try {
                inviaRichiestaPartecipanti(registro.getRichiesta(idGara));
                sleep(TIMEOUT);
                registro.chiudiGare(idGara);
                System.out.println("Gara "+ idGara + " chiusa");
                esitoGara();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void inviaRichiestaPartecipanti(Richiesta r){
            try {
                String ric="RICHIESTA - "+r.getIdEnte()+" - "+r.getDesc()+ " - "+r.getImpMax();
                byte[] buf=ric.getBytes();
                InetAddress g=InetAddress.getByName(group);
                DatagramPacket p=new DatagramPacket(buf, buf.length, g, groupPort);
                ms.send(p);
                System.out.println("Invio richiesta: "+r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void esitoGara(){
            try {
                Offerta winner=registro.getOffertaMigliore(idGara);
                if(winner==null)
                    winner=new Offerta(-1, idGara, -1);
                System.out.println(winner);
                String message="ESITO - "+idGara+ " - "+winner.getIdPartecipante() + " - "+ winner.getImporto();
                byte[] b=message.getBytes();
                InetAddress g=InetAddress.getByName(group);
                DatagramPacket p= new DatagramPacket(b, b.length, g, groupPort);
                ms.send(p);
                ObjectOutputStream out=new ObjectOutputStream(registro.getSocketEnte(idGara).getOutputStream());
                out.writeObject(winner);
                System.out.println("Inviata offerta vincente all'ente");
                registro.getSocketEnte(idGara).close();
            } catch (Exception e) {
                System.out.println("Errore "+e);
            }
        }
        

    }


}
