import java.net.Socket;
import java.util.*;
public class TestSocket{

    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        System.out.print("Inserisci l'host> ");
        String host=s.nextLine();
        System.out.println();
        System.out.print("Inserisci la porta (80 se test http)> ");
        int porta=s.nextInt();
        System.out.println();
        s.close();
        int timeout=10000; //10 secondi
        Socket so=SocketOpener.openSocket(host, porta, timeout);
        if(so==null)
            System.out.println("Impossibile aprire il socket");
        else
            System.out.println(so);
        System.out.println();
        System.out.println("Fine programma");
    }
}