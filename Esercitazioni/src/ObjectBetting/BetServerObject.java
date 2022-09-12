package ObjectBetting;

import java.net.*;
import java.util.*;
import java.io.*;

public class BetServerObject {
    
    private AcceptBet ab;
    private DenyBet db;
    private int port=8001;
    private InetAddress group;
    private Calendar limite;
    private HashMap<Integer,Scommessa> scommesse;

    public BetServerObject(String g, Calendar limite){
        try {
            group=InetAddress.getByName(g);
            this.limite=limite;
            scommesse=new HashMap<>();
            ab=new AcceptBet(port);
            ab.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        int client=8002;
        try {
            String gruppo="230.0.0.1";
            Calendar l=Calendar.getInstance();
            l.add(Calendar.MINUTE,1);
            BetServerObject bso=new BetServerObject(gruppo, l);
            System.out.println("Scommesse aperte");
            bso.accettaScommesse();
            bso.rifiutaScommesse();
            int vincitore=((int)Math.random()*12+1);
            System.out.println("Ha vinto: "+vincitore);
            LinkedList<Scommessa> vincitori=bso.estrazioneVincitori(vincitore);
            bso.comunicaVincitori(vincitori, bso.group, client);
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public LinkedList<Scommessa> estrazioneVincitori(int v){
        LinkedList<Scommessa> ret=new LinkedList<>();
        for(int i=0; i<scommesse.size(); i++){
            Scommessa sc=scommesse.get(i);
            if(sc.equals(new Scommessa(v,0, null)))
                ret.addLast(sc);
        }
        return ret;
    }

    public void comunicaVincitori(LinkedList<Scommessa> vincitori, InetAddress add, int port){
        try {
            MulticastSocket ms=new MulticastSocket(port);
            byte[] buff=new byte[512];
            String m="";
            int quota=12;
            ListIterator<Scommessa> lit=vincitori.listIterator();
            while(lit.hasNext()){
                Scommessa s=lit.next();
                m+=s.getIp()+" "+(s.getPuntata()*quota)+"\n";
            }
            buff=m.getBytes();
            DatagramPacket p=new DatagramPacket(buff, buff.length, add, port);
            ms.send(p);
        } catch (Exception e) {
            System.out.println("error "+e);
        }
    }




    class AcceptBet extends Thread{

        private int port;
        private ServerSocket ss;
        private boolean accept;

        public AcceptBet(int port){
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
            try {
                while(accept){
                    Calendar now=Calendar.getInstance();
                    ss.setSoTimeout((int) (limite.getTimeInMillis()-now.getTimeInMillis()));
                    Socket s=ss.accept();
                    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                    Scommessa sc= (Scommessa)ois.readObject();
                    scommesse.put(sc.getID(), sc);
                    oos.writeObject("Scommessa accettata");
                    ois.close();
                    oos.close();
                    s.close();
                }
            } catch (SocketTimeoutException e) {
                accept=false;
                System.err.println("Tempor scaduto "+e);
            }catch(Exception e){
                System.err.println(e);
            }
        }

    }

    class DenyBet extends Thread{

        private boolean closed;
        private int port;
        private ServerSocket ss;

        public DenyBet(int port){
            this.port=port;
            closed=true;
            try {
                ss=new ServerSocket(port);
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        @Override
        public void run() {
            try {
                while(closed){
                    Socket s=ss.accept();
                    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject("Scommessa rifiutata");
                    oos.close();
                }
                ss.close();
            } catch (Exception e) {
                System.out.println("errore "+e);
            }
        }
    }

    public void accettaScommesse(){
        try {
            ab.join();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void rifiutaScommesse(){
        db=new DenyBet(port);
        db.start();
    }

}
