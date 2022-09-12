package Betting;

import java.net.*;
import java.io.*;
import java.util.*;

public class BetClient {
    
    private Socket s;
    private MulticastSocket ms;
    private int port=8001;
    private int serverPort=8002;
    private InetAddress myAddress;
    private InetAddress groupAddress;
    private static int ID=1;

    public BetClient(String myAddress, String groupAddress){
        try {
            this.myAddress=InetAddress.getByName(myAddress);
            this.groupAddress=InetAddress.getByName(groupAddress);
            s=new Socket(myAddress,port);
        } catch (Exception e) {
            System.out.println("Errore lato client "+e);
        }
    }

    public static void main(String[] args) throws IOException{
        BetClient bc=new BetClient("localhost","230.0.0.1");
        System.out.println("Place a bet on a horse: ");
        if(bc.placeBet()){
            System.out.println("Waiting for results...");
            bc.getResult();
        }
        ID++;
        System.out.println("Fine client"+ ID);
    }

    private boolean placeBet(){
        String esito=null;
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Numero cavallo della bet: ");
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            new Lettore(br).start();
            String bet=sc.nextLine();
            pw.println(bet);
            esito=br.readLine();
            sc.close();
            pw.close();
            //s.close();
            br.close();
            System.out.println(esito);
        }
        catch (IOException e) {
            System.out.println("Errore lato client "+e);
        }
        return esito.equals("Scommessa accettata");
    }

    private void getResult(){
        try {
            this.ms=new MulticastSocket(serverPort);
            ms.joinGroup(groupAddress);
            byte buffer[]=new byte[512];
            DatagramPacket p=new DatagramPacket(buffer, buffer.length);
            ms.receive(p);
            String risultato=new String(p.getData());
            System.out.println("Vincitori: ");
            System.out.println(risultato);
        } catch (Exception e) {
            System.out.println("Errore lato client "+e);
        }
    }





}

class Lettore extends Thread{

    private BufferedReader br;

    public Lettore(BufferedReader br){
        this.br=br;
    }

    public void run(){
        try {
            while(true){
                String line=br.readLine();
                if(line==null)
                    break;
                System.out.println(line);
            }
            br.close();
        } catch (Exception e) {
           System.out.println("Errore "+e );
        }
    }
}
