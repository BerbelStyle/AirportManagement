package local;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class ConveyorBelt {
    
    private final ArrayList <String> belt;
    private int counter;
    private JTextArea text;
    private String suitcase;
    private final Lock lock = new ReentrantLock();
    private final Condition availableBelt = lock.newCondition();
    private final Condition busyBelt = lock.newCondition();
    private JProgressBar progress;
    private final Record record = new Record();
    
    public ConveyorBelt() {
        belt = new ArrayList<>();
        counter = 0;
    }
    
    public void leaveCase(String id, String idm, long t1) {    
        try {
            lock.lock();
            while(belt.size() >= 8) { //No space on the belt
                try {
                    busyBelt.await(); //Passengers wait
                } catch (InterruptedException e) {}
            }
            counter++;
            progress.setValue(counter);
            progress.setString(""+counter);
            suitcase = idm;
            belt.add(suitcase);  
            long t2 = (new Date()).getTime();
            double time = (double)(t2-t1)/1000; 
            record.saveData(id + " leaves "
            + suitcase + " on the belt. ["+time+"s]");
            text.setText(belt.toString());  
            availableBelt.signalAll(); 
        } finally {
            lock.unlock(); 
        }
    }
    
    public String takeCase(String id, long t1) {
        try {
            lock.lock();
            while(belt.isEmpty()) { //No suitcases on the belt
                try {
                    availableBelt.await(); //Employees wait
                } catch (InterruptedException e) {}
            }  
            counter--;
            progress.setValue(counter);
            progress.setString(""+counter);
            int x = belt.size()-1;
            suitcase = belt.get(x);
            belt.remove(x);    
            long t2 = (new Date()).getTime();
            double time = (double)(t2-t1)/1000; 
            record.saveData(id + " carries "
            + suitcase + " to the plane.["+time+"s]");
            text.setText(this.toString());
            busyBelt.signalAll();    
            return suitcase;
        } finally {
            lock.unlock();
        }
    }
    
    public String getSuitcase() {
        return suitcase;
    }
    
    public void setText(JTextArea x) {
        this.text = x;  
    }
    
    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }
    
    @Override
    public String toString() {
        if (belt.isEmpty()) {
            return "There are no suitcases on the belt.";
        }
        return "" + belt;
    }
}
