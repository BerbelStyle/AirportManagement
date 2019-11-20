package remote;
import java.io.*;
import java.net.*;

public class Client
{
    public static void main(String args[])
    {
        Socket client;
        int portNumber = 5000; //Should use a high number so the port is not being used.
        String host = "127.0.0.1"; //Local IP, works with other IPs aswell (same network).
        DataInputStream in;
        String responseBelt;
        String responsePlane;
        RemoteConsole c = new RemoteConsole();
        c.setVisible(true);
        try
        {
            client = new Socket(host, portNumber);   //Created socket to connect to portNumber 5000 del servidor
            in = new DataInputStream(client.getInputStream());  //IO channels
            while (true) {                                     
            responseBelt = in.readUTF(); //We receive the response
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