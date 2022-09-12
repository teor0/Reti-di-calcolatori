package A7Nov2019;

import java.net.*;
import java.util.*;
import java.io.*;


public class Server {

    private static final int clientPort=1111;
    private static final int shopPort=2222;
    private ArrayList<Negozio> negozi=new ArrayList<>();




    class HandlerRichieste extends Thread{

        private ServerSocket ss;

        @Override
        public void run() {
            try {
                ss=new ServerSocket(clientPort);
                while(true){
                    Socket s=ss.accept();
                    ObjectInputStream in=new ObjectInputStream(s.getInputStream());
                    Richiesta r=(Richiesta)in.readObject();
                    new HandlerRisposta(r).start();
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    class HandlerRisposta extends Thread{

        private Socket s;
        private Richiesta r;

        public HandlerRisposta(Richiesta r){
            this.r=r;
        }


        @Override
        public void run() {
            try {
                for(int i=0; i<negozi.size(); i++){
                    s=new Socket(negozi.get(i).getAdd(),shopPort);
                    
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }


}
