package A30gen2018;

import java.net.*;

public class Venditore {

    private String groupAdd="230.0.0.1";
    private static final int udpPort=6789;


    public Venditore(String add){
        this.groupAdd=add;
    }

    public String getGroupAdd() {
        return groupAdd;
    }

    public static int getUdpport() {
        return udpPort;
    }

    public void setGroupAdd(String groupAdd) {
        this.groupAdd = groupAdd;
    }

    

    
}
