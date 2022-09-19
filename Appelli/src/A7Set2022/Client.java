package A7Set2022;

import java.io.*;
import java.net.*;

public class Client {
    
    private String cateSel;
    private static final int pPort=3000;
    private static final int cPort=4000;
    private int password;

    public Client(String cateSel){
        this.cateSel=cateSel;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPassword() {
        return password;
    }

    public String getCategoriaSelezionata() {
        return cateSel;
    }

    public boolean chiediPrenotazione(){
        boolean esito=false;
        try {
            Socket s=new Socket("localhost", pPort);//sarebbe servizi.unical.it
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(this.getCategoriaSelezionata());
            ObjectInputStream in=new ObjectInputStream(s.getInputStream());
            String risposta=(String)in.readObject();
            String[] parti=risposta.split("-");
            if(Integer.parseInt(parti[1])!=0)
                esito=true;
            this.setPassword(Integer.parseInt(parti[1]));
            s.close();
        } catch (Exception e) {
           System.err.println(e);
        }
        return esito;
    }

    public void avvioConferenza(){
        try {
            Socket s=new Socket("localhost", cPort);
            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
            out.writeObject(this.getPassword());
            ObjectInputStream in=new ObjectInputStream(s.getInputStream());
            String link=(String) in.readObject();
            System.out.println(link);
            out.close();
            in.close();
            s.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) {
        Client c=new Client("Scuola");
        if(c.chiediPrenotazione())
            c.avvioConferenza();
        System.out.println("fine client");
    }
    
}
