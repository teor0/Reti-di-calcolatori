package EsercizioObject;
import java.io.*;
public class Libro implements Serializable{

    private int year;
    private String title;
    private String genre;
    private double price;

    public Libro(String title, String genre, double price, int year){
        this.title=title;
        this.genre=genre;
        this.price=price;
        this.year=year;
    }

    public int getYear(){
        return this.year;
    }

    public String getGenre(){
        return this.genre;
    }

    public String getTitle(){
        return this.title;
    }

    public double getPrice(){
        return this.price;
    }

    @Override
    public String toString(){
        String s="Titolo libro "+ getTitle() + "\n"+  "Genere "+ getGenre()+ "\n"+
        "Prezzo "+ getPrice()+"€"+ "\n"+"Anno "+ getYear();
        return s;
    }

    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(!( o instanceof Libro) || this==null)
            return false;
        Libro l=(Libro) o;
        if(this.title.equals(l.getTitle()) && this.year==l.getYear())
            return true;
        return false;
    }

}