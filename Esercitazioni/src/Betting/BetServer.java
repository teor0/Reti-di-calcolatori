package Betting;

import java.net.*;
import java.io.*;
import java.util.*;

public class BetServer {

    private BetDenyer bd;
    private BetAccepter ba;
    private Calendar limite;
    private int port;
    private HashMap<Integer, Scommessa> scommesse;

    public BetServer(int port, Calendar limite){
        this.port=port;
        this.limite=limite;
        scommesse=new HashMap<>();
        ba=new BetAccepter(port);
        ba.start();
    }

    public static void main(String[] args) {
        int serverPort=8001;
        int clientPort=8002;
        try {
            Calendar l=Calendar.getInstance();
            l.add(Calendar.MINUTE, 1 );
            BetServer bt=new BetServer(serverPort, l);
            System.out.println("Scommesse aperte");
            bt.accettaScommesse();
            bt.rifiutaScommesse();
            int vincente=((int) Math.random()*12+1);
            System.out.println("Ha vinto il cavallo: "+vincente);
            LinkedList<Scommessa> vincitori=bt.controllaScommesse(vincente);
            InetAddress multiAddress=InetAddress.getByName("230.0.0.1");
            bt.comunicaVincitori(vincitori, multiAddress, clientPort);
            Thread.sleep(120000);
            bt.resetServer();
        } catch (Exception e) {
            System.out.println("Errore lato server "+e);
        }
    }
    

    class BetAccepter extends Thread{

        private int port;
        private ServerSocket ss;
        private boolean accept;
    
        public BetAccepter(int port){
            try {
                this.port=port;
                ss=new ServerSocket(port);
                accept=true;
            } catch (Exception e) {
                System.out.println("Errore lato server "+e);
            }
        }
    
        @Override
        public void run(){
            while(accept){
                try {
                    Calendar now=Calendar.getInstance();
                    ss.setSoTimeout((int)(limite.getTimeInMillis()-now.getTimeInMillis()));
                    Socket sa=ss.accept();
                    BufferedReader br=new BufferedReader(new InputStreamReader(sa.getInputStream()));
                    PrintWriter pw=new PrintWriter(sa.getOutputStream(),true);
                    String line=br.readLine();
                    StringTokenizer st=new StringTokenizer(line);
                    int cavallo=Integer.parseInt(st.nextToken());
                    long puntata=Long.parseLong(st.nextToken());
                    InetAddress ip=sa.getInetAddress();
                    Scommessa s=new Scommessa(cavallo,puntata,ip);
                    int key=s.getID();
                    scommesse.put(key, s);
                    pw.println("Scommessa accettata");
                    pw.close();
                    sa.close();
                    System.out.println("Scommessa ricevuta: "+ip+cavallo+puntata);
                } catch (SocketTimeoutException e) {
                    accept=false;
                    System.out.println("Tempo a disposizione per le scommesse terminato"+e);
                }catch(IOException ex){
                    System.out.println("Errore "+ex);
                }
            }//while
            try {
                ss.close();
            } catch (Exception e) {
                System.out.println("Errore "+e);
            }
        }
    }

    class BetDenyer extends Thread{

        private int port;
        private ServerSocket ss;
        private boolean closed;
        
        public BetDenyer(int port){
            try {
                this.port=port;
                ss=new ServerSocket(port);
                closed=true;
            } catch (IOException e) {
                System.out.println("Errore "+e);
            }
        }
    
        public void reset(){
            closed=false;
        }
    
        @Override
        public void run(){
            try {
             while(closed){
                Socket s=ss.accept();
                PrintWriter pw=new PrintWriter(s.getOutputStream(), true);
                pw.println("Scommesse chiuse.");
                pw.close();
                s.close();
                System.out.println("Scommessa rifiutata");
             }//while
             ss.close();   
            } catch (IOException e) {
                System.out.println("Errore "+e);
            }
        }
    
    }//BetDenyer

    public void accettaScommesse(){
        try {
            ba.join();
        } catch (InterruptedException e) {
            System.out.println("Errore "+e);
        }
    }

    public void rifiutaScommesse(){
        bd=new BetDenyer(port);
        bd.start();
    }

    public void resetServer(){
        bd.reset();
    }

    public LinkedList<Scommessa> controllaScommesse(int vincitore){
        LinkedList<Scommessa> elenco=new LinkedList<>();
        for(int i=0; i<scommesse.size(); i++){
            Scommessa s=scommesse.get(i);
            if(s.equals(new Scommessa(vincitore, 0,null)))
                elenco.addLast(s);
        }//for
        return elenco;
    }

    public void comunicaVincitori(LinkedList<Scommessa> vincitori, InetAddress add, int port){
        ListIterator<Scommessa> lit=vincitori.listIterator();
        try {
            MulticastSocket ms=new MulticastSocket();
            byte[] buffer=new byte[256];
            String m="";
            int quota=12;
            while(lit.hasNext()){
                Scommessa s=lit.next();
                m+=s.getScommettitore()+ " "+ (s.getPuntata()*quota)+"\n";
            }
            buffer=m.getBytes();
            DatagramPacket packet=new DatagramPacket(buffer, buffer.length,add,port);
            ms.send(packet);
        } catch (IOException e) {
            System.out.println("Errore "+ e);
        }
    }


}



