package A7Nov2019.WSDL;

import java.util.*;

public class GestoreServizi {
    
    private HashMap<Integer,Esame> exams=new HashMap<>();
    private HashMap<String, Studente> students=new HashMap<>();

    public void addStudente(Studente s){
        this.students.put(s.getMatricola(), s);
    }

    public void addEsame(Esame e){
        this.exams.put(e.getId(),e);
    }

    public ArrayList<Integer> EsamiStudente(String matricola){
        ArrayList<Integer> esami=new ArrayList<>();
        Collection<Esame> e=exams.values();
        Iterator<Esame> it=e.iterator();
        while(it.hasNext()){
            Esame es=it.next();
            if(es.getStudenti().contains(matricola))
                esami.add(es.getId());
        }
        return esami;
    }

    public ArrayList<Studente> StudentiPerEsame(int id,int voto){
        Esame e=exams.get(id);
        ArrayList<Studente> studenti=new ArrayList<>();
        for(String matricola: e.getStudenti()){
            if(e.getVoti().get(matricola)==voto)
                studenti.add(students.get(matricola));
        }
        return studenti;
    }

    public static void main(String[] args) {
        GestoreServizi gs=new GestoreServizi();
        Esame e=new Esame(70);
        e.addVoto("mar418", 26);
        e.addVoto("kne310", 17);
        e.addVoto("nvb018", 26);
        Esame e2=new Esame(14);
        e.addVoto("kne310", 19);
        Studente s=new Studente("mar418","Marco", "Pasta");
        Studente s2=new Studente("kne310","Kristian", "Bollato");
        Studente s3=new Studente("nvb018","Luigi", "Rossi");
        e2.addVoto("kne310", 22);
        gs.addStudente(s);
        gs.addStudente(s2);
        gs.addStudente(s3);
        gs.addEsame(e);
        gs.addEsame(e2);
        ArrayList<Integer> ret1=gs.EsamiStudente(s2.getMatricola());
        for(int i=0; i<ret1.size();i++)
            System.out.println(ret1.get(i));
        ArrayList<Studente> ret2=gs.StudentiPerEsame(70,26);
        for(int j=0; j<ret2.size(); j++)
            System.out.println(ret2.get(j));
    }

}
