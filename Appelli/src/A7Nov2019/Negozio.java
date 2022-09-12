package A7Nov2019;

import java.net.InetAddress;

public class Negozio{

    private int id;
    private InetAddress add;

    public Negozio(int id){
        this.id=id;
        try {
            add=InetAddress.getByName("localhost");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public InetAddress getAdd() {
        return add;
    }

    public int getId() {
        return id;
    }
    
}
