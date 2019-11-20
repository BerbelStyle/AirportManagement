package local;

import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Plane {
    
    private final ArrayList <String> suitcases;
    private JProgressBar progress;
    private JTextArea text;
    private int counter;
    
    public Plane() {
        suitcases = new ArrayList<>();   
    }
    
    public ArrayList <String> getSuitcases() {
        return suitcases;
    }

    public void setText(JTextArea x) {
        this.text = x;
    }
    
    public JTextArea getText() {
        return text;
    }
    
    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }
    
    //The employee carries the suitcase to the plane
    public synchronized void bringCase(String suitcase) {
            suitcases.add(suitcase);
            counter++;
            getText().setText(toString().substring(1));
            progress.setValue(counter);
            progress.setString(""+counter);
    }
    
    @Override
    public String toString() {
        return "" + suitcases;
    }
    
}
