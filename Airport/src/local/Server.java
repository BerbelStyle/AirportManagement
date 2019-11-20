package local;
import java.io.*;
import java.net.*;

public class Server 
{
    public static void main(String args[]) throws InterruptedException {
        
        ServerSocket server;
        Socket connection;
        DataOutputStream out;
        LocalConsole c;
        int counter = 0;
        
        try
        {
            server = new ServerSocket(5000); // Creamos un ServerSocket en el puerto 5000
            System.out.println("Server started....");   
            while (true)
            {
                connection = server.accept();     // Esperamos una conexión
                c = new LocalConsole();
                c.setVisible(true);
                counter++;
                System.out.println("Connection #"+counter+" from: "+connection.getInetAddress().getHostName());                         
                out  = new DataOutputStream(connection.getOutputStream());// Abrimos los canales de E/S
                while (true) {
                String mensaje1 = c.getBelt();
                String mensaje2 = c.getPlane();
                out.writeUTF(mensaje1);  // Le respondemos
                out.writeUTF(mensaje2);
                }
            }
        } catch (IOException e) {}
    }
}