package TracciaIndex;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class IndexServer {

    private Map<File,InetAddress> data;
    private final int uPort=3000;
    private final int tPort=4000;
    private ServerSocket server;
    private DatagramSocket uSocket;

    public IndexServer(){
        //uso una struttura dati sincronizzata
        data=Collections.synchronizedMap(new HashMap<>());
        inizio();
    }

    private void inizio(){
        try {
            server=new ServerSocket(tPort);
            uSocket=new DatagramSocket(uPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new HandlerFile().start(); //thread per storageserver
        new HandlerClient().start(); //thread per i client
    }

    public static void main(String[] args) {
        IndexServer is=new IndexServer();
    }

    class HandlerFile extends Thread{

        @Override
        public void run() {
            try {
                while(true){
                    byte[] b=new byte[512];
                    DatagramPacket p=new DatagramPacket(b, b.length);
                    System.out.println("Aspetto un pacchetto...");
                    uSocket.receive(p);
                    String msg=new String(p.getData());
                    System.out.println(msg);
                    String[] parti=msg.split("#");
                    String[] keyword=parti[1].split(",");
                    File f=new File(parti[0], keyword, "");
                    System.out.println("Memorizzo il file nella struttura dati");
                    data.put(f, p.getAddress());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class HandlerClient extends Thread{


        @Override
        public void run() {
            try {
                while(true){
                    System.out.println("Attendo ricerca...");
                    Socket s=server.accept();
                    BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String msg=br.readLine();
                    System.out.println("Ricerca: "+ msg);
                    String[] parti=msg.split("#");
                    String[] keyword=parti[1].split(",");
                    System.out.format("Ricevo la ricerca con filename %s e key %s %n", parti[0], parti[1]);
                    String ret="";
                    synchronized(data){
                        for(Entry<File, InetAddress> entry: data.entrySet()){
                            File f=entry.getKey();
                            if(f.getFilename().equals(parti[0])){
                                boolean trovato=true;
                                for(int i=0; i<keyword.length && trovato; i++){
                                    trovato=false;
                                    for(int j=0; j<f.getKeywords().length; j++)
                                        if(keyword[i].equals(f.getKeywords()[j])){
                                            trovato=true;
                                            break;
                                        }
                                }
                                if(trovato)
                                    ret+=entry.getValue().toString();
                            }//if
                        }//for
                    }//sync

                    System.out.println("Invio risposta "+ret);
                    PrintWriter pw=new PrintWriter(s.getOutputStream(), true);
                    pw.println(ret);
                    br.close();
                    pw.close();
                    s.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
