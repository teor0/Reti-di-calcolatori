package GareAppaltoPlus;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Ente extends Thread {

    private static String add="127.0.0.1";
    private static int porta=2000, countOpere=0, idEnte;

    public Ente(int id){
        idEnte=id;
    }

    @Override
    public void run() {
        try {
            sleep(new Random().nextInt(60000));
            Socket s=new Socket(add,porta);
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            Richiesta ric=new Richiesta(idEnte, "Opera "+(++countOpere), 500000);
            out.writeObject(ric);
            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
            Offerta migliore=(Offerta) ois.readObject();
            System.out.println("Offerta migliore "+ migliore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i=0; i<10; i++)
            new Ente(i).start();
    }
    
}
