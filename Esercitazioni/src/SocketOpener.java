import java.io.IOException;
import java.net.*;
public class SocketOpener extends Thread{
    
    private String host;
    private int porta;
    private Socket s;

    public void run(){
        try{
            s=new Socket(host,porta);
        }
        catch(IOException e){
            System.out.println("Errore "+ e);
        }
    }

    //costruttore
    public SocketOpener(String host, int porta){
        this.host=host;
        this.porta=porta;
        this.s=null;
    }

    public Socket getSocket(){
        return s;
    }

    public static Socket openSocket(String host, int port, int timeout){
        SocketOpener opener=new SocketOpener(host, port);
        opener.start();
        try {
            opener.join(timeout); //attesa fino al timeout
        } catch (InterruptedException e) {
            System.out.println("errore "+ e);
        }
        return opener.getSocket();
    }
    
}
