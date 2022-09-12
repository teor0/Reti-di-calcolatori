package TestCompetenza;

import java.net.*;
import java.util.*;
import java.io.*;

public class Tourist{
    public static void main(String[] args) {
        try {
            Scanner sc= new Scanner(System.in);
            InetAddress ip=InetAddress.getByName("localhost");
            Socket s= new Socket(ip,1050);
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            new Lettore(br).start();
            String line=sc.nextLine();
            if(line==null)
                s.close();
            pw.println(line);
            sc.close();
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
        try{
            while(true){
                String line=br.readLine();
                if(line==null)
                    break;
                else
                    System.out.println(line);
            }
            br.close();
        }catch(IOException e){
            System.out.println("Errore "+e);
        }
    }


}
