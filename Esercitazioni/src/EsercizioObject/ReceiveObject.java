package EsercizioObject;
import java.net.*;
import java.io.*;
public class ReceiveObject {

    public static void main(String[] args) {
        try{
            InetAddress ad= InetAddress.getByName("localhost");
            Socket s=new Socket(ad,7518);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            String greeting= (String) ois.readObject();
            System.out.println(greeting);
            Libro l=(Libro) ois.readObject();
            String title=l.getTitle();
            String genre=l.getGenre();
            double price=l.getPrice();
            int year=l.getYear();
            System.out.println(title);
            System.out.println(genre);
            System.out.println(price);
            System.out.println(year);
            System.out.println(l);
            String end=(String) ois.readObject();
            System.out.println(end);
            ois.close();
            s.close();
        }
        catch(Exception e){
            System.out.println("Errore"+ e);
        }
        
    }
    
}
