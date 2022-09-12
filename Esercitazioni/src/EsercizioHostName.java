import java.net.*;
import java.util.Scanner;
public class EsercizioHostName{

	public static void main(String [] args) throws UnknownHostException{
		System.out.println("Inizio programma");
		printLocalIPAddress();
		System.out.println("Inserisci un hostname> ");
		Scanner s=new Scanner(System.in);
		String hn=s.nextLine();
		printRemoteIPAddress(hn);
		printAllRemoteIPAddress(hn);
		s.close();
		System.out.println("Fine programma");
	}


  private	static void printLocalIPAddress(){
		try{
			InetAddress localhost=InetAddress.getLocalHost();
			System.out.println("Hostname: "+ localhost.getHostName());
			System.out.println("Indirizzo IP: "+ localhost.getHostAddress());
		}
		catch(UnknownHostException e){
			System.out.println("ERROR");
		}
	}

	private	static void printRemoteIPAddress(String Hostname){
		try{
			InetAddress nodo=InetAddress.getByName(Hostname);
			System.out.println("Hostname: "+ nodo.getHostName());
			System.out.println("Indirizzo IP: "+ nodo.getHostAddress());
		}
		catch(UnknownHostException e){
			System.out.println("ERROR");
		}
	}

	private static void printAllRemoteIPAddress(String nome){
		try {
			InetAddress[] nodi=InetAddress.getAllByName(nome);
			int n=nodi.length;
			for(int i=0; i<n; i++){
				System.out.println("Hostname ["+i+"] "+nodi[i].getHostName());
				System.out.println("Indirizzo IP ["+ i +"] "+ nodi[i].getHostAddress());
			}
		} catch (UnknownHostException e) {
			System.out.println("ERRORE");
		}		
	}

}
