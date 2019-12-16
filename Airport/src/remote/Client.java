package remote;
import java.io.*;
import java.net.*;

/**
 * Cliente. Buscará un servidor para recibir información.
 * @author jorge
 */
public class Client
{
    /**
     * Se iniciará un cliente que intentará conectarse a un servidor dada su
     * IP y el puerto objetivo. Una vez conectado, esperará recibir mensajes
     * respecto al estado de la cinta y del avión.
     * @param args 
     */
    public static void main(String args[])
    {
  
        //Variables
        Socket client;
        int portNumber = 5000; //Número alto para que no esté siendo utilizado.
        String host = "127.0.0.1"; /*IP local, vale con otras también (Siempre
        y cuando se encuentren en la misma red, a no ser que se abra un puerto)
        */
        DataInputStream in;
        String responseBelt;
        String responsePlane;
        RemoteConsole c = new RemoteConsole();
        c.setVisible(true);
        
        try
        {
            client = new Socket(host, portNumber); /*Socket que se coenctará
            al servidor, dado un número de puerto. */
            in = new DataInputStream(client.getInputStream());  /*Canal de 
            entrada  
            */
            while (true) {                                     
            responseBelt = in.readUTF(); //Recibe la respuesta
            responsePlane = in.readUTF(); 
                c.setBelt(responseBelt);
                c.setPlane(responsePlane);
            }       
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}