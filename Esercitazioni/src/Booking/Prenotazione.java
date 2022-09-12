package Booking;

import java.io.Serializable;
import java.net.*;

public class Prenotazione implements Serializable{
    
    private InetAddress ip;
    private String nome;
    private int port;


    public Prenotazione(String nome, InetAddress add,int port){
        ip=add;
        this.nome=nome;
        this.port=port;
    }

    public String getNome(){
        return this.nome;
    }

    public InetAddress getIp(){
        return this.ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o){
        if(this==null || !(o instanceof Prenotazione))
            return false;
        if(this==o)
            return true;
        Prenotazione p=(Prenotazione) o;
        return p.getIp().equals(this.getIp());
    }



}
