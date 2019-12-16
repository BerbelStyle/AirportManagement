package local;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Historial. Se encargará de recoger los datos de las acciones realizadas
 * por los pasajeros y empleados.
 * @author jorge
 */
public class Record {
    
    //Variables
    private int counter;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    /**
     * Constructor.
     */
    public Record() {  
        this.counter = 1;
    }
    
    /**
     * Se guardan los datos de las acciones tanto de pasajeros como de
     * empleados en un archivo de texto.
     * @param event acción realizada.
     */
    public void saveData(String event) {   
        FileWriter record = null;
        PrintWriter record_w;
        try{
            if (counter == 1) { /* Condición necesaria para que se genere 
                un archivo nuevo en cada ejecución, pero no en cada acción 
                guardada. */
                record = new FileWriter("C:\\Users\\jorge\\Documents\\"
                + "NetBeansProjects\\airport\\Record.txt"); 
                record_w = new PrintWriter(record);
                record_w.println("___________________________RECORD"
                + "___________________________\n"); //Estética del fichero.
                counter++;
            } else {
            record = new FileWriter("C:\\Users\\jorge\\Documents\\"
                + "NetBeansProjects\\airport\\Record.txt", true);
            }
            record_w = new PrintWriter(record);   
            //Lo que se añade al fichero:
            record_w.println(dateFormat.format(date)+": "+event+"\n");
        }catch(IOException e){
        }finally{
            try{
                if (null != record)
                    record.close();
            }catch(IOException e2){
            }
        }   
    }
}
