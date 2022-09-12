package Booking;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
public class BookingServer {
    
    private AcceptBooking ab;
    private DenyBooking db;
    private int limite;
    private HashSet<Prenotazione> prenotazioni;
    private int port=5670;

    public BookingServer(int limite){
        this.limite=limite;
        prenotazioni=new HashSet<>();
        ab=new AcceptBooking(port);
        ab.start();
    }

    public static void main(String[] args) {
        try{
            InetAddress group=InetAddress.getByName("230.0.0.1");
            int groupPort=3002;
            BookingServer bs=new BookingServer(10000);
            System.out.println("Inizio prenotazioni");
            bs.accettaPrenotazioni();
            bs.stopPrenotazioni();
            System.out.println("Fine prenotazioni");
            //bs.comunicaPrenotazioni(group, groupPort);
            bs.getPrenotazioni();
        }
        catch(Exception e){
            System.out.println("errore "+e);
        }
    }


    public void getPrenotazioni(){
        Iterator<Prenotazione> it= this.prenotazioni.iterator();
        while(it.hasNext()){
            Prenotazione p=it.next();
            System.out.println("Prenotazione da:"+ p.getPort());
        }
    }

    class AcceptBooking extends Thread{

        private int port;
        private boolean accept;
        private ServerSocket ss;

        public AcceptBooking(int port){
            try {
                this.port=port;
                accept=true;
                ss=new ServerSocket(port);     
            } catch (Exception e) {
                System.out.println("errore "+e);
            }
        }

        @Override
        public void run() {
            while(accept){
                try {
                    ss.setSoTimeout(limite);
                    Socket s=ss.accept();
                    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                    Prenotazione p=(Prenotazione)ois.readObject();
                    prenotazioni.add(p);
                    String ok="Prenotazione accettata";
                    oos.writeObject(ok);
                    s.close();
                } catch (SocketTimeoutException e) {
                    accept=false;
                    System.out.println("Tempo scaduto");
                }
                catch(Exception ex){
                    System.out.println("errore "+ex);
                }
            }//while
            try {
                ss.close();
            } catch (Exception e) {
                System.out.println("errore "+e);
            }
        }
    }

    class DenyBooking extends Thread{

        private boolean closed;
        private int port;
        private ServerSocket ss;

        public DenyBooking(int port){
            try {
                this.port=port;
                closed=true;
                ss=new ServerSocket(port);
            } catch (Exception e) {
              System.out.println("errore "+e);
            }
        }

        @Override
        public void run() {
            while(closed){
                try {
                    Socket s=ss.accept();
                    ObjectOutputStream ois=new ObjectOutputStream(s.getOutputStream());
                    ois.writeObject("Prenotazioni chiuse");
                    ois.close();
                    s.close();
                } catch (IOException e) {
                    System.out.println("errore "+e);
                }
            }
        }
    }

    public void accettaPrenotazioni(){
        try {
            ab.join();
        } catch (Exception e) {
            System.out.println("errore "+e);
        }
    }

    public void stopPrenotazioni(){
        try {
            db=new DenyBooking(port);
            db.start();
        } catch (Exception e) {
           System.out.println("error "+e);
        }
    }

    public void comunicaPrenotazioni(InetAddress group, int port){
        try {
            MulticastSocket ms= new MulticastSocket(port);
            byte[] buff=new byte[512];
            String greet="Benvenuto";
            buff=greet.getBytes();
            DatagramPacket p=new DatagramPacket(buff,buff.length,group,port);
            ms.send(p);

        } catch (Exception e) {
            System.out.println("error "+e);
        }

    }

}
