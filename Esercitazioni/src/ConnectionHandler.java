import java.io.*;
import java.net.*;
public class ConnectionHandler extends Thread{

    private Socket s;

    public ConnectionHandler(Socket s){
        this.s=s;
    }

    public void run(){
        try{
            System.out.format("Nuova connessione gestita %s. %n", s.toString());
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            pw.println("Scrivi BYE per terminare la connessione");
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
            System.out.format("Connessione con %s terminata. %n", s.toString());
            s.close();
            pw.close();
            br.close();
        }
        catch(IOException e){
            System.out.println("Errore "+e);
        }
    }
    
}
