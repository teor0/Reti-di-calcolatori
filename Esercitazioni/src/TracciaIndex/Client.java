package TracciaIndex;

import java.io.*;
import java.net.*;

public class Client {
    
    public static void main(String[] args) {
        try {
            File f=new File("prova", new String[]{"a", "b","c"}, "...");
            Socket s=new Socket("localhost",2000);
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(f);
            out.flush();
            out.close();
            s.close();
            Thread.sleep(3000);
            f=new File("cane", new String[]{"a", "b"}, "...");
            s=new Socket("localhost",2000);
            out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(f);
            out.flush();
            out.close();
            s.close();
            Thread.sleep(10000);
            String ricerca="cane#a,b";
            System.out.println("Ricerco tutti i file indicati da: "+ricerca);
            s=new Socket("localhost", 4000);
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            pw.println(ricerca);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            String ret=br.readLine();
            System.out.println("Risposta ricerca: "+ret);
            br.close();
            pw.close();
            s.close();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
