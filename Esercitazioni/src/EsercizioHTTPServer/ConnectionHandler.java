package EsercizioHTTPServer;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
public class ConnectionHandler extends Thread{

    private Socket s;
    private BufferedReader br;
    private DataOutputStream dos;
    public ConnectionHandler(Socket s){
        this.s=s;
        try {
            br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            dos=new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            try{
                s.close();
            }
            catch(IOException ex){
                System.out.println("Errore "+ex);
            }
            return;
        }
        this.start();
    }

    public void run(){
        try{
            System.out.format("Nuova connessione gestita %s. %n", s.toString());
            String request=br.readLine();
            System.out.println("Request "+request);
            StringTokenizer st=new StringTokenizer(request);
            if(st.countTokens()>=2 && st.nextToken().equals("GET")){
                if(st.nextToken().startsWith("/"))
                    request=request.substring(1);
                if(request.endsWith("/") || request.equals(""))
                    request=request + "index.html";
                if(request.indexOf("..")!=-1 || request.startsWith("/"))
                    dos.writeBytes("403 Forbidden. "+"You do not have enough privileges to read: "+request+"\r\n");
                else{
                     File f=new File(request);
                     reply(dos,f);
                }
             }
             else{
                dos.writeBytes("400 Bad request\r\n");
             }
             s.close();
        }
        catch(IOException e){
            System.out.println("Errore "+e);
        }
        catch(Exception ex){
            System.out.println("Errore "+ex);
        }
    }
    
    public static void reply(DataOutputStream d, File f) throws Exception{
        try {
            DataInputStream dis=new DataInputStream(new FileInputStream(f));
            int l=(int) f.length();
            byte buffer[]= new byte[l];
            dis.readFully(buffer);
            d.writeBytes("HTTP/1.0 200 OK\r\n");
            d.writeBytes("Content-Lenght: "+buffer.length+ "\r\n");
            d.writeBytes("Content-Type: text/html \r\n\r\n");
            d.write(buffer);
            d.flush();
            dis.close();
        } catch (FileNotFoundException e) {
            d.writeBytes("404 Not found \r\n");
        }
    }
}
