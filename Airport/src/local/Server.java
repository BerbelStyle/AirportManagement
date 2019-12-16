package local;
import java.io.*;
import java.net.*;

/**
 * Servidor. Esperará a un cliente para pasarle información.
 * @author jorge
 */
public class Server 
{
    /**
     * Se abrirá un servidor, que esperará a la conexión de algún cliente 
     * para empezar a enviarle información sobre el estado de la cinta y 
     * el avión.
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String args[]) throws InterruptedException {
        
        //Variables
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
                connection = server.accept();     // Espera una conexión
                c = new LocalConsole();
                c.setVisible(true);
                counter++;
                System.out.println("Connection #"+counter+" from: "+connection.getInetAddress().getHostName());                         
                out  = new DataOutputStream(connection.getOutputStream());// Abrimos canal de salida
                while (true) {
                String mensaje1 = c.getBelt();
                String mensaje2 = c.getPlane();
                out.writeUTF(mensaje1);  // Le responde
                out.writeUTF(mensaje2);
                }
            }
        } catch (IOException e) {}
    }
}