import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
public class EchoServer {
    public static void main(String[] args) throws IOException{
        Scanner s=new Scanner(System.in);
        try{
        ServerSocket ss=new ServerSocket(8189);
        Socket incoming=ss.accept();
        BufferedReader br=new BufferedReader(new InputStreamReader(incoming.getInputStream()));
        PrintWriter pw=new PrintWriter(incoming.getOutputStream(),true);
        pw.println("");
        pw.println("");
        pw.println("");
        pw.println("Request accepted, hello. Enter BYE to quit the connection");
        pw.println("");
        pw.println("");
        String ciao="Ciao";
        boolean more=true;
        while(more){
            String line=br.readLine();
            if(line== null){
                more=false;
            }
            else if(line.toLowerCase().equals(ciao.toLowerCase()))
                pw.println("ECHO: "+ ciao +" a te");
            else{
                pw.println("ECHO: "+ line);
                if(line.trim().equals("BYE"))
                    more=false;
            }
        }
        incoming.close();
        ss.close();
        s.close();
        }
        catch(IOException e){
            System.out.println("Errore "+e);
        }
        System.out.println("Fine programma");
    }
}
