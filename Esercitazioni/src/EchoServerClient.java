import java.net.*;
import java.io.*;
public class EchoServerClient {
    public static void main(String[] args) {
        try {
            Socket s=new Socket("localhost",8189);
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw.println("Prova invio");
            pw.println("Prova(2)");
            pw.println("");
            pw.println("BYE");
            boolean more=true;
            while(more){
                String line=br.readLine();
                if(line==null)
                    more=false;
                else
                    System.out.println(line);
            }
            s.close();
            pw.close();
            br.close();
        } catch (IOException e) {
            System.err.println("Errore "+e);
        }
    }
}
