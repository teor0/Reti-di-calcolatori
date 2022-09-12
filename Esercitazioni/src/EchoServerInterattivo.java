import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServerInterattivo {
    public static void main(String[] args) {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Inserisci la porta da utilizzare> ");
            int porta=sc.nextInt();
            Socket s=new Socket("localhost",porta);
            Boolean more=true;
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            Lettore l=new Lettore(br);
            l.start();
            while(more){
                String line=sc.nextLine();
                if(line.equals("BYE"))
                    more=false;
                else if(line.equals("EXIT")){
                    pw.println("BYE");
                    more=false;
                }
                pw.println(line);
            }
            sc.close();
            l.interrupt();
            s.close();
            pw.close();
            br.close();
            System.out.println("Fine client");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("errore "+e);
        }   
    }
}

/*
 * Il lettore in pratica permette la corretta comunicazione tra client ed echo server
 * In particolare fornisce l'output su console
 */
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
