package TracciaIndex;

import java.io.ObjectInputStream;
import java.net.*;
import java.util.*;

public class StorageServer {

    private final int cPort=2000; //porta per connessioni client
    private HashMap<String, File> data;
    private String indexServerAddress;
    private final int indexServerPort=3000;
    private ServerSocket server;

    public StorageServer(String add){
        this.indexServerAddress=add;
        data=new HashMap<>();
        System.out.println("Fase di avvio...");
        inizia();
    }

    public void inizia(){
        try {
            server=new ServerSocket(cPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(true){
            memorizzaFile();
        }
    }

    private void memorizzaFile(){
        try {
            System.out.println("In attesa di memorizzare un file...");
            Socket s=server.accept();
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            File f=(File) ois.readObject();
            ois.close();
            s.close();
            data.put(f.getFilename(), f);
            System.out.println("Ho salvato: "+ f);
            DatagramSocket ds=new DatagramSocket();
            String msg=f.getFilename()+"#";
            for(String key:f.getKeywords())
                msg+=key+",";
            byte [] b=msg.getBytes();
            InetAddress add=InetAddress.getByName(indexServerAddress);
            DatagramPacket p=new DatagramPacket(b, b.length, add, indexServerPort);
            ds.send(p);
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        StorageServer stS=new StorageServer("localhost");
    }
    
}
