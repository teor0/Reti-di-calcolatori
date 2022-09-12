package EsercizioStudente;
import java.io.*;
import java.net.*;
import java.util.*;
public class ReceiveSerializedObj {
    public static void main(String[] args) {
        try {
            Scanner s=new Scanner(System.in);
            System.out.println("Inserisci il numero di studenti da memorizzare");
            int dim=s.nextInt();
            HashSet<Integer> studenti= new HashSet<>();
            for(int i=0; i<dim; i++){
                System.out.format("Inserisci matricola studente %d ", i);
                int mat=s.nextInt();
                studenti.add(mat);
            }
            s.close();
            Socket so=new Socket("localhost",5562);
            ObjectInputStream ois=new ObjectInputStream(so.getInputStream());
            String greetMsg= (String)ois.readObject();
            System.out.println(greetMsg);
            Studente x =(Studente)ois.readObject();
            if(!studenti.contains(x.getMatricola())){
                System.out.println("Siamo spiacenti ma non è presente nessuno studente con la matricola inviata");
                System.out.println("Fine connessione");
                ois.close();
                so.close();
                System.exit(0);
            }
            System.out.println("Matricola "+x.getMatricola());
            System.out.println("Cognome "+x.getCognome());
            System.out.println("Nome "+x.getNome());
            System.out.println("Corso di Laurea "+x.getCdl());
            String endMsg=(String)ois.readObject();
            System.out.println(endMsg);
            ois.close();
            so.close();
        }catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore "+e);
        }
    }
    
}
