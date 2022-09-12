package ConnessioniURL;

import java.net.*;
import java.io.*;
import java.util.*;

public class URLTest {
    
    public static void main(String[] args) {

        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Inserisci l'URL della pagina");
            String u=sc.nextLine();
            sc.close();
            URL url= new URL(u);
            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
            boolean more=true;
            while(more){
                String line=br.readLine();
                if(line==null)
                    more=false;
                else
                    System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }
    }
}
