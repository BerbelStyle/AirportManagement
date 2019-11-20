package local;

import java.util.Date;
import javax.swing.JTextArea;

public class Employee extends Thread {
 
    private final ConveyorBelt myBelt;
    private String id;
    private String suitcase;
    private Plane plane;
    private JTextArea activity;
    private final Passage passage;
    
    public Employee(ConveyorBelt belt, Passage passage)
    {
        this.myBelt = belt;
        this.passage = passage;
    }
    
    public void setPlane(Plane plane) {
        this.plane = plane;
    }
    
    public void setID(String name) {
        this.id = name;
    }
    
    public String getID() {
        return id;
    }
    
    public void setActivity(JTextArea act) {
        this.activity = act;
    }

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
