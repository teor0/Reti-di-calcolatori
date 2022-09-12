import java.io.*;

public class Orario implements Serializable{
	
	private int ore;
	private int minuti;
	
	public Orario(int ore, int minuti){
		this.ore=ore;
		this.minuti=minuti;
	}
	
	public void setOre(int ore){
		this.ore=ore;
	}
	
	public void setMinuti(int minuti){
		this.minuti=minuti;
	}
	
	public int getOre(){
		return ore;
	}
	
	public int getMinuti(){
		return minuti;
	}
	
	public boolean equals(Object o){
		if(this==o)
			return true;
		else if(this==null || !(o instanceof Orario))
			return false;
		Orario or=(Orario) o;
		return this.getOre()==or.getOre() && this.getMinuti()==or.getMinuti();
	}
	
	public static void main(String[] args){
		Orario o=new Orario(17,34);
		System.out.println(o.getOre());
		System.out.println(o.getMinuti());		
	}
	
	
	
	
}