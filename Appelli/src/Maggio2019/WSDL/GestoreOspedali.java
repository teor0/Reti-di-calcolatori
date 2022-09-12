package Maggio2019.WSDL;

import java.util.*;

public class GestoreOspedali {
    
    private HashMap<String, Ospedale> ospedali;

    public GestoreOspedali(){
        ospedali=new HashMap<>();
    }

    public void addOspedale(Ospedale o){
        ospedali.put(o.getCodice(), o);
    }

    public Ospedale MaxOspedali(String citta){
        Iterator<Ospedale> it=ospedali.values().iterator();
        int postiLiberi=0;
        String codice=null;
        Ospedale os=it.next();
        while(it.hasNext()){
            if(os.getPostiLetto()-os.getPazienti()>postiLiberi && os.getCitta().equals(citta)){
                postiLiberi=os.getPostiLetto()-os.getPazienti();
                codice=os.getCodice();
            }
            os=it.next();
        }
        return ospedali.get(codice);
    }

    public String CittaRatio(){
        Iterator<Ospedale> it=ospedali.values().iterator();
        int postiLiberi=Integer.MAX_VALUE;
        String citta=null;
        Ospedale os=it.next();
        while(it.hasNext()){
            int liberi=os.getPostiLetto()-os.getPazienti();
            if(liberi<postiLiberi){
                postiLiberi=liberi;
                citta=os.getCitta();
            }
            os=it.next();
        }
        return citta;
    }

    public static void main(String[] args) {
        GestoreOspedali go=new GestoreOspedali();
        Scanner sc=new Scanner(System.in);
        for(int i=0; i<3; i++){
            System.out.println("Inserisci l'ospedale");
            go.addOspedale(new Ospedale(sc.nextLine(), sc.nextLine(), Integer.parseInt(sc.nextLine()),Integer.parseInt(sc.nextLine())));
        }
        System.out.println(go.CittaRatio());  
        System.out.println("fine");
    }

}
