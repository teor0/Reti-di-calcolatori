import java.io.*;
import java.net.*;

public class DownloadPaginaHTTP {
    public static void main(String[] args) {
        try {
            String URL="eu.httpbin.org";
            Socket s=new Socket(URL,80);
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            pw.println("GET /anything HTTP/1.1");
            pw.println("Host: "+URL);
            pw.println();
            while(true){
                String linea= br.readLine();
                if(linea==null)
                    break;
                else
                    System.out.println(linea);
            }
            s.close();
        } catch (IOException e) {
            System.out.println("Errore "+ e);
        }
    }
}
