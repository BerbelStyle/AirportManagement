package local;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    
    private int counter;
    Date date = new Date();
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    public Record() {  
        this.counter = 1;
    }
    
    public void saveData(String event) {   
        FileWriter record = null;
        PrintWriter record_w;
        try{
            if (counter == 1) { //Needed condition to recreate the record in each execution of the program
                record = new FileWriter("C:\\Users\\jorge\\Documents\\"
                + "NetBeansProjects\\aeropuerto\\Record.txt");
                record_w = new PrintWriter(record);
                record_w.println("__________________________RECORD"
                + "__________________________\n");
                counter++;
            } else {
            record = new FileWriter("C:\\Users\\jorge\\Documents\\"
                + "NetBeansProjects\\aeropuerto\\Record.txt", true);
            }
            record_w = new PrintWriter(record);   
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
