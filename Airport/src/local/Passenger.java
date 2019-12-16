package local;

import java.util.Date;

/**
 * Pasajero. Tiene dos maletas y se encargará de dejarlas en la cinta.
 * @author jorge
 */
public class Passenger extends Thread {
    
    //Variables
    private final String id;
    private final ConveyorBelt myBelt;
    
    /**
     * Constructor.
     * @param id nombre del empleado
     * @param belt cinta en la que dejará sus maletas.
     */
    public Passenger(String id, ConveyorBelt belt) {
        this.id = id;
        this.myBelt = belt;
    }
    
    /**
     * El pasajero intentará dejar sus dos maletas.
     */
    @Override
    public void run() {
            long t1 = (new Date()).getTime();
            for(int i = 0; i < 2; i++)
            {      
                try
                {
                    sleep((int)(500+500*Math.random()));
                } catch(InterruptedException e){ }
                    String suitcase = id + "-S"+(i+1); //id de la maleta
                    myBelt.leaveCase(id, suitcase, t1);
            }
    }
}
    

