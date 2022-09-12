package A26Feb2015;


import java.net.*;
import java.io.*;
public class Server {
    
    public void rispondi(){
        while(true){
            try {
                String risp="";
                DatagramSocket ds=new DatagramSocket();
                byte[] b=new byte[512];
                DatagramPacket p=new DatagramPacket(b, b.length);
                ds.receive(p);
                risp=new String(p.getData());
                ds.close();
                String[] parti=risp.split("-");
                String hostname=parti[0];
                int porta=Integer.parseInt(parti[1]);
                int A=Integer.parseInt(parti[2]);
                int B=Integer.parseInt(parti[3]);
                int C=A+B;
                Risposta ri=new Risposta(C);
                InetAddress add=InetAddress.getByName(hostname);
                Socket sClient=new Socket(add,porta);
                ObjectOutputStream out=new ObjectOutputStream(sClient.getOutputStream());
                out.writeObject(ri);
                sClient.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s=new Server();
        s.rispondi();
    }

}
