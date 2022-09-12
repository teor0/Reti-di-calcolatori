package TestCompetenza;

import java.net.*;
import java.util.*;

public class BookingServer {

    private Integer[] rooms;

    public BookingServer(int n){
        rooms=new Integer[n];
        for(int i=0; i<n; i++)
            rooms[i]=0;
    }

    public int getRoom(int i){
        int r=this.rooms[i];
        return r;
    }

    public void setRoom(int i, int id){
        this.rooms[i]=id;
    }

    public int size(){
        return rooms.length;
    }

    public boolean allBooked(){
        boolean flag=true;
        for(int i=0; i<rooms.length; i++)
            if(rooms[i]==0)
                flag=false;
        return flag;
    }
    
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Insert the number of available rooms");
        BookingServer bs=new BookingServer(sc.nextInt());
        try {
            ServerSocket ss=new ServerSocket(1050);
            while(true){
                Socket s=ss.accept();
                new ConnectionHandler(bs,s).start();
                if(bs.allBooked()){
                    System.out.println("No more rooms available");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Errore "+e);
        }
    }

}
