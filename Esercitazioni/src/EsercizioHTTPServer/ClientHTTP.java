package EsercizioHTTPServer;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientHTTP{

    public static void main(String [] args){
        try {
            Socket s=new Socket("localhost",8189);
            System.out.format("Connessione riuscita %s. %n",s.toString());
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            new Lettore(br).start();
            while(true){
                Scanner sc=new Scanner(System.in);
                String line=sc.nextLine();
                if(line==null || line.equals("EXIT")){
                    s.close();
                    break;
                }
                pw.println(line);
            }
            s.close();
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }
    }

}

class Lettore extends Thread{

    private BufferedReader br;

    public Lettore(BufferedReader br){
        this.br=br;
    }

    @Override
    public void run(){
        boolean more=true;
        try {
            while(more){
                String line=br.readLine();
                if(line==null)
                    more=false;
                else
                    System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }
    }
}