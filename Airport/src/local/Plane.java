package local;

import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * Avión. Específicamente el almácen de un avión. Se depositarán maletas
 * en éste.
 * @author jorge
 */
public class Plane {
    
    //Variables
    private final ArrayList <String> suitcases;
    private JProgressBar progress;
    private JTextArea text;
    private int counter;
    
    /**
     * Constructor
     */
    public Plane() {
        suitcases = new ArrayList<>();   
    }
    
    /**
     * @return las maletas que hay en el avion.
     */
    public ArrayList <String> getSuitcases() {
        return suitcases;
    }

    /**
     * Se le asigna un JTextArea de la interfaz.
     * @param x JTextArea de la interfaz.
     */
    public void setText(JTextArea x) {
        this.text = x;
    }
    
    /**
     * @return el contenido del avión.
     */
    public JTextArea getText() {
        return text;
    }
    
    /**
     * Se le asigna un JProgressBar de la interfaz.
     * @param progress contador de maletas en el avión.
     */
    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }
    
    /**
     * Garantizando exclusión mutua (no pueden entrar los dos empleados
     * al avión a la vez), se dejan maletas en el avión.
     * @param suitcase la maleta que se va a dejar en el avión.
     */
    public synchronized void bringCase(String suitcase) {
            suitcases.add(suitcase);
            counter++;
            getText().setText(toString());
            progress.setValue(counter);
            progress.setString(String.valueOf(counter));
    }
    
    /**
     * @return las maletas que hay en el avión.
     */
    @Override
    public String toString() {
        return "" + suitcases;
    }
    
}
