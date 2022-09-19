package A7Set2022;

import java.io.*;
import java.util.*;
import java.net.*;


public class Server {

    private static final int pPort=3000;
    private static final int cPort=4000;
    private Registro r;

    public Server(){
        r=new Registro();
        r.addCategoria("Scuola");
        r.addCategoria("Fiscale");
        r.addCategoria("Catasto");
        r.addCategoria("Sociale");
    }

    public void avvioGiornata(){
        new Timer().start();
    }


    class Timer extends Thread{
        private Date d;

        @Override
        public void run() {
            while(true){
                d=Calendar.getInstance().getTime();
                if(d.getHours()==8 && d.getMinutes()==0 && d.getSeconds()==0)
                    new HandlerPrenotazioni(r).start();
                else if(d.getHours()==9 && d.getMinutes()==0 && d.getSeconds()==0)
                    new HandlerComunicazioni(r).start();
                
            }
        }
    }

    class HandlerPrenotazioni extends Thread{

        private Registro re;
        private ServerSocket ss;
        private int ID=0;

        public HandlerPrenotazioni(Registro re){
            this.re=re;
            try {
                ss=new ServerSocket(pPort);
                ss.setSoTimeout(60*1000*60);
            } catch (SocketTimeoutException e) {
                new HandlerFinePrenotazioni().start();
            }catch (Exception ex){
                System.err.println(ex);
            }
        }


        @Override
        public void run() {
            try {
                Socket s;
                while(true){
                    s=ss.accept();
                    ObjectInputStream in=new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                    String cat=(String) in.readObject();
                    if(re.prenotazionePossibile(cat)){
                        int nounce=new Random().nextInt(0,9999999);
                        re.addPrenotazione(cat, ID++, nounce);
                        out.writeObject(ID+"-"+nounce);
                    }
                    else{
                        out.writeObject(ID+"-"+0);
                    }
                    s.close();
                }
            } catch (Exception e) {
                System.err.println(e);
            }

        }
    }

    class HandlerFinePrenotazioni extends Thread{


        private ServerSocket ss;

        public HandlerFinePrenotazioni(){
            try {
                ss=new ServerSocket(pPort);
            } catch (SocketTimeoutException e) {
                new HandlerFinePrenotazioni().start();
            }catch (Exception ex){
                System.err.println(ex);
            }
        }


        public void run() {
            try {
                Socket s;
                while(true){
                    s=ss.accept();
                    ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                    out.writeObject("Fine prenotazioni-0");
                    s.close();
                }
            } catch (Exception e) {
                System.err.println(e);
            }

        }
    }


    class HandlerComunicazioni extends Thread{

        private Registro re;
        private ServerSocket ss;

        public HandlerComunicazioni(Registro re){
            this.re=re;
            try {
                ss=new ServerSocket(cPort);
                ss.setSoTimeout(60*1000*60*5);//5 ore di timer
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        @Override
        public void run() {
            try {
                Socket s;
                while(true){
                    s=ss.accept();
                    int pw=re.passwordComunicazione("Scuola", 1);
                    ObjectInputStream in=new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                    int rs=(Integer)in.readObject();
                    if(pw==rs){
                        out.writeObject("http:ciaobello");
                        sleep(new Random().nextLong(9,31)*1000);
                    }
                    s.close();
                }
            } catch (Exception e) {
            }
        }
    }




    
}
