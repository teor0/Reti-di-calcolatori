package Booking;
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class BookClient {

    private Socket s;
    private MulticastSocket ms;
    private int groupPort=3002;
    private int serverPort=5670;
    private InetAddress myIP;
    private InetAddress groupIP;

    public BookClient(String mioIP, String group){
        try {
            this.myIP=InetAddress.getByName(mioIP);
            this.groupIP=InetAddress.getByName(group);
            s=new Socket(myIP, serverPort);
        } catch (Exception e) {
            System.out.println("Errore "+e);
        }
    }

    public static void main(String[] args) {
        try {
            BookClient bc=new BookClient("localhost", "230.0.0.1");
            System.out.println("Prenoto stanza...");
            if(bc.prenota())
                System.out.println("Stanza prenotata");
        } catch (Exception e) {
           System.err.println(e);
        }
    }
    
    public boolean prenota(){
        String esito="";
        try {
            Scanner sc=new Scanner(System.in);
            ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            System.out.println("Inserisci il tuo nome");
            String name=sc.nextLine();
            Prenotazione p=new Prenotazione(name, myIP,s.getLocalPort());
            oos.writeObject(p);
            esito=(String)ois.readObject();
            System.out.println(esito);
            sc.close();
            oos.close();
            ois.close();
        } catch (Exception e) {
            System.out.println("errore "+e);
        }
        return esito.equals("Prenotazione accettata");
    }

}
