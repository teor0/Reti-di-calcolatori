import java.net.*;
import java.io.*;

public class ThreadEchoServer {
    public static void main(String[] args) {
        try {
            int port=3340;
            ServerSocket ss=new ServerSocket(port);
            while(true){
                System.out.format("Il %s è in attesa di una connessione sulla porta %s.%n",ss.toString(),Integer.toString(port));
                Socket s=ss.accept();
                new ConnectionHandler(s).start(); //gestisco la connessione
                System.out.println("");
            }
        } catch (IOException e) {
           System.out.println("Errore "+e);
        }
    }
}

