package local;

import java.util.Date;
import javax.swing.JTextArea;

/**
 * Empleado. Se dedicará a recoger maletas de pasajeros y llevarlas a su
 * respectivo avión.
 * @author jorge
 */
public class Employee extends Thread {
 
    //Variables
    private final ConveyorBelt myBelt;
    private String id;
    private String suitcase;
    private Plane plane;
    private JTextArea activity;
    private final Pass passage;
    
    /**
     * Constructor.
     * @param belt cinta en la que buscará maletas.
     * @param passage 
     */
    public Employee(ConveyorBelt belt, Pass passage)
    {
        this.myBelt = belt;
        this.passage = passage;
    }
    
    /**
     * Se le asigna un avión al empleado.
     * @param plane avión en el que trabajará el empleado.
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
    }
    
    /**
     * Se le pone un nombre al empleado.
     * @param name nombre a dar al empleado.
     */
    public void setID(String name) {
        this.id = name;
    }
    
    /**
     * @return el nombre del empleado.
     */
    public String getID() {
        return id;
    }
    
    /**
     * Se le asigna un JTextArea de la interfaz.
     * @param act JTextArea de la interfaz.
     */
    public void setActivity(JTextArea act) {
        this.activity = act;
    }

    /**
     * Mientras exista el empleado, cogerá maletas de la cinta y 
     * las llevará al avión.
     */
    @Override
    public void run()
    {
        while(true) {
            long t1 = (new Date()).getTime();
            passage.watch(id);
            try
            {
                sleep((int)(400+300*Math.random()));
            } catch(InterruptedException e){ }              
                passage.watch(id);
                suitcase = myBelt.takeCase(id, t1);
                activity.setText("Carrying " + suitcase
                + " to the plane.");  
                plane.bringCase(suitcase); 
            try 
            {
                sleep((int)(400+300*Math.random()));
            } catch(InterruptedException e){ } 
                if (!activity.getText().contains("Resting.")) {
                    activity.setText("Going back to the belt...");    
                }                                
        }   
    }
}
