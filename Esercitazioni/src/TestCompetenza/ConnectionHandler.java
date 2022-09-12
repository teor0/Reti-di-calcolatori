package TestCompetenza;
import java.net.*;
import java.io.*;

public class ConnectionHandler extends Thread{
    
    private Socket s;
    private BookingServer bs;
   
    public ConnectionHandler(BookingServer b,Socket s){
        this.bs=b;
        this.s=s;
    }

    public void book(int i, int id){
        try {
            if(bs.getRoom(i)==0)
                bs.setRoom(i, id);
            else{
                PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
                pw.println("Stanza già prenotata da "+ bs.getRoom(i));
                s.close();
            }
        } catch (IOException e) {
            System.out.println("Errore "+e);
        } catch(IndexOutOfBoundsException ex){
            try {
                PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
                pw.println("La stanza non esiste");
                s.close();
            } catch (IOException e) {
                System.out.println("Errore "+e);
            }
        }
    }

    @Override
    public void run(){
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw=new PrintWriter(s.getOutputStream(),true);
            if(bs.allBooked())
                pw.println("We are sorry but we are full. See you next time");
            else{
                System.out.format("New customer %s! %n", s.toString());
                pw.println("");
                pw.println("Welcome to the booking service customer!\n"+ "Please book one of the "+ bs.size()+ " rooms");
                int r=Integer.valueOf(br.readLine());
                int id=s.getPort();
                book(r,id);
                pw.println("Room booked");
                pw.println("Thanks for choosing us");
                s.close();
                br.close();
                pw.close();
            }
        } catch (IOException e) {
            System.out.println("Errore "+e);
        }catch(IllegalArgumentException ex){
            System.out.println("");
        }
    }

}
