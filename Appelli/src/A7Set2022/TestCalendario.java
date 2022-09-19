package A7Set2022;

import java.util.*;

public class TestCalendario {
    
    public static void main(String[] args) {
        Date d;
        while(true){
            d=Calendar.getInstance().getTime();
            System.out.println(d.getHours()+ " "+d.getMinutes());
            if(d.getHours()==19 && d.getMinutes()==38){
                System.out.println("avvio");
                break;
            }
        }
    }


}
