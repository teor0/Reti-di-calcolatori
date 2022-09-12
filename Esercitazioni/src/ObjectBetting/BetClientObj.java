package ObjectBetting;
import java.net.*;
import java.io.*;
import java.util.*;
public class BetClientObj {
    
    private MulticastSocket ms;
    private Socket s;
    private InetAddress myIP;
    private InetAddress groupIP;
    private int port=8001;
    private int serverPort=8002;

    public BetClientObj(String ip, String group){
        try{
             myIP=InetAddress.getByName(ip);
             groupIP=InetAddress.getByName(group);
             s=new Socket(myIP, port);
        }catch(Exception e){
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        try {
            BetClientObj bc=new BetClientObj("localhost","230.0.0.1");
            System.out.println("Scometti su un cavallo");
            if(bc.bet()){
                System.out.println("Attendo i risultati...");
                bc.risultati();
            }
            System.out.println("Fine");
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    public boolean bet(){
        String esito="";
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Inserisci cavallo");
            int cavallo=sc.nextInt();
            System.out.println("Inserisci puntata");
            long puntata=sc.nextLong();
            Scommessa sco=new Scommessa(cavallo, puntata,myIP);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            oos.writeObject(sco);
            esito=(String)ois.readObject();
        } catch (Exception e) {
           System.err.println(e);
        }
        return esito.equals("Scommessa accettata");
    }

    public void risultati(){
        try {
            this.ms=new MulticastSocket(serverPort);
            ms.joinGroup(groupIP);
            byte[] buf=new byte[512];
            DatagramPacket p=new DatagramPacket(buf, buf.length);
            ms.receive(p);
            String ret=new String(p.getData());
            System.out.println("Vincitori: ");
            System.out.println(ret);
        } catch (Exception e) {
        }
    }








}
