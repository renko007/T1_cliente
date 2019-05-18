// Java implementation for a client 
// Save file as Client.java 
  
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
// Client class 
public class Cliente 
{ 
    public static void main(String[] args) throws IOException  
    { 
        try
        { 
            Scanner scn = new Scanner(System.in); 
              
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("192.168.0.12"); 
      
            // establish the connection with server port 5056 
            Socket s = new Socket(ip, 4445); 
      
            // obtaining input and out streams 
            DataInputStream dis;
            DataOutputStream dos;
      
            while (true)  
            { 
            	dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                System.out.println(dis.readUTF()); 
                String tosend = scn.nextLine(); 
                dos.writeUTF(tosend); 
                if(tosend.equals("ls")) {
                	int expected=dis.readInt();
                	int cont=0;
                	while (cont<expected) {
                		String received = dis.readUTF(); 
                        System.out.println(received);
                		cont++;
                	}
                }
                else if(tosend.contains("get")){
                	InputStream in = null;
                    OutputStream out = null;
                	try {
                        in = s.getInputStream();
                    } catch (IOException ex) {
                        System.out.println("Can't get socket input stream. ");
                    }

                    try {
                        out = new FileOutputStream(tosend.split(" ")[1]);
                    } catch (FileNotFoundException ex) {
                        System.out.println("File not found. ");
                    }

                    byte[] bytes = new byte[16*1024];

                    int count;
                    Thread.sleep(1000);
                    while ((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }
                    out.close();
                    System.out.println("Descarga realizada");
                }
                else if(tosend.contains("put")) {
                	String[] parts = tosend.split(" ");
                	String name=parts[1];
                	System.out.println(name);
                    byte [] bytes =new byte [16*1024];
                    InputStream in = new FileInputStream(name);
                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        dos.write(bytes, 0, count);
                    }
                    System.out.println("Subida realizada");
                    Thread.sleep(1000);
                    in.close();
                }
                else if(tosend.contains("delete")) {
                	
                }
                else if(tosend.equals("Exit")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }  
                else {
                    System.out.println(dis.readUTF()); 
                }
            } 
              
            // closing resources 
            scn.close(); 
            dis.close(); 
            dos.close();
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
    } 
} 