package EsercizioStudente;
import java.io.*;
import java.util.*;
import java.net.*;

public class SendSerializedObj {
    public static void main(String[] args) {
        try {
            Scanner s=new Scanner(System.in);
            System.out.println("Inserisci nome studente ");
            String nome=s.nextLine();
            System.out.println("Inserisci cognome studente ");
            String cognome=s.nextLine();
            System.out.println("Inserisci corso di laurea studente ");
            String cdl=s.nextLine();
            System.out.println("Inserisci matricola studente ");
            int mat=s.nextInt();
            s.close();
            ServerSocket ss=new ServerSocket(5562);
            Socket accepter=ss.accept();
            ObjectOutputStream oos=new ObjectOutputStream(accepter.getOutputStream());
            oos.writeObject("Benvenuto");
            Studente stud=new Studente(mat, nome, cognome, cdl);
            oos.writeObject(stud);
            oos.writeObject("Arrivederci");
            accepter.close();
            oos.close();
            ss.close();
        } catch (IOException e) {
            System.err.println("Errore "+e);
        }
        
    }
}
