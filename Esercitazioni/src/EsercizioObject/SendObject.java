package EsercizioObject;
import java.net.*;
import java.io.*;
import java.util.*;
public class SendObject{


    public static void main(String[] args) {
        try {
            Scanner s=new Scanner(System.in);
            System.out.println("Inserisci titolo libro");
            String titolo=s.nextLine();
            System.out.println("Inserisci genere libro");
            String genere=s.nextLine();
            System.out.println("Inserisci prezzo libro");
            double prezzo=Double.parseDouble(s.nextLine());
            System.out.println("Inserisci anno libro");
            int anno=Integer.parseInt(s.nextLine());
            Libro l=new Libro(titolo, genere, prezzo, anno);
            s.close();
            ServerSocket ss=new ServerSocket(7518);
            Socket so=ss.accept();
            ObjectOutputStream oos=new ObjectOutputStream(so.getOutputStream());
            oos.writeObject("Welcome");
            oos.writeObject(l);
            oos.writeObject("Bye");
            oos.close();
            so.close();
            ss.close();
        } catch (IOException e) {
            System.out.println("errore "+ e);
        }
    }



}