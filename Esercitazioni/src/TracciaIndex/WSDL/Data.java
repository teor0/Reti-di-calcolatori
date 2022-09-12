import java.io.*;
import java.util.*;

public class Data implements Serializable{
	
	private int giorno;
	private int mese;
	private int anno;
	
	public Data(int giorno, int mese, int anno){
		this.giorno=giorno;
		this.mese=mese;
		this.anno=anno;
	}
	
	public int getGiorno(){
		return giorno;
	}
	
	public int getMese(){
		return mese;
	}
	
	public int getAnno(){
		return anno;
	}
	
	public void setGiorno(int g){
		this.giorno=g;
	}
	
	public void setMese(int m){
		this.mese=m;
	}
	
	public void setAnno(int a){
		this.anno=a;
	}
	
	@Override
	public boolean equals(Object o){
		if(this==o)
			return true;
		else if(this==null || !(o instanceof Data))
			return false;
		Data d=(Data) o;
		return this.getGiorno()==d.getGiorno() && this.getMese()==d.getMese() && this.getAnno()==d.getAnno();
	}
	
	public static void main(String[] args){
		Data d=new Data(4,5,2001);
		System.out.format("Giorno: %d, mese: %d, anno: %d %n", d.getGiorno(), d.getMese(), d.getAnno());
	}
	
	
}