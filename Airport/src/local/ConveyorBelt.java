package local;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * Cinta de transporte. Aquí los pasajeros dejarán maletas y los empleados
 * las recogerán.
 * @author jorge
 */
public class ConveyorBelt {
    
    //Variables
    private final ArrayList <String> belt;
    private final int max;
    private int counter;
    private JTextArea text;
    private String suitcase;
    private final Lock lock = new ReentrantLock();
    private final Condition availableBelt = lock.newCondition();
    private final Condition busyBelt = lock.newCondition();
    private JProgressBar progress;
    private final Record record = new Record();
    
    /**
     * Constructor.
     * @param max la capacidad máxima de la cinta.
     */
    public ConveyorBelt(int max) {
        this.belt = new ArrayList<>();
        this.counter = 0;
        this.max = max;
    }
    
    /**
     * Los pasajeros intentarán dejar la maleta en la cinta. Si ven que hay
     * otro pasajero dejando una maleta, se esperarán. No hay orden
     * específico: el más rápido es el que la deja.
     * @param id nombre del pasajero.
     * @param ids identficación de la maleta.
     * @param t1 tiempo inicial de la acción.
     */
    public void leaveCase(String id, String ids, long t1) {    
        try {
            lock.lock(); //El pasajero se pone el primero para dejar la maleta.
            while(belt.size() >= max) { //Cinta llena, el pasajero espera.
                try {
                    busyBelt.await(); 
                } catch (InterruptedException e) {}
            }
            //Cinta libre, el pasajero deja la maleta.
            counter++;
            progress.setValue(counter);
            progress.setString(String.valueOf(counter));
            suitcase = ids;
            belt.add(suitcase);  
            long t2 = (new Date()).getTime();
            double time = (double)(t2-t1)/1000; //Tiempo total de la acción.
            record.saveData(id + " leaves "
            + suitcase + " on the belt. ["+time+"s]"); //Se guarda la acción.
            text.setText(belt.toString());  
            availableBelt.signalAll(); 
        } finally {
            lock.unlock(); //El pasajero se va, dejando hueco a los demás.
        }
    }
    
    /**
     * Los empleados intentarán coger maletas de la cinta. Si la cinta esta 
     * vacía, esperarán. Si no, cogerán la primera maleta que vean.
     * @param id nombre del empleado.
     * @param t1 tiempo inicial de la acción.
     * @return la maleta cogida por el empleado.
     */
    public String takeCase(String id, long t1) {
        try {
            lock.lock(); //El empleado se pone el primero para coger la maleta.
            while(belt.isEmpty()) { //Cinta vacía, el empleado espera.
                try {
                    availableBelt.await(); 
                } catch (InterruptedException e) {}
            }  
            //Cinta con maleta/s, el empleado coge una maleta.
            counter--;
            progress.setValue(counter);
            progress.setString(""+counter);
            int x = belt.size()-1;
            suitcase = belt.get(x);
            belt.remove(x);    
            long t2 = (new Date()).getTime();
            double time = (double)(t2-t1)/1000; //Tiempo total de la acción.
            record.saveData(id + " takes "
            + suitcase + " to the plane.["+time+"s]"); //Se guarda la acción.
            text.setText(this.toString());
            busyBelt.signalAll();    
            return suitcase;
        } finally {
            lock.unlock(); //El empleado se va, dejando hueco al otro.
        }
    }
    
    /**
     * Se le asigna un JTextArea de la interfaz.
     * @param x JTextArea de la interfaz.
     */
    public void setText(JTextArea x) {
        this.text = x;  
    }
    
    /**
     * Se le asigna una JProgressBar de la interfaz.
     * @param progress JProgressBar de la interfaz.
     */
    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }
    
    /**
     * @return el contenido de la cinta.
     */
    @Override
    public String toString() {
        if (belt.isEmpty()) {
            return "There are no suitcases on the belt.";
        }
        return "" + belt;
    }
}
