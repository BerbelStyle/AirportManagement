package local;

import java.util.Date;

public class Passenger extends Thread {
    
    private final String id;
    private final ConveyorBelt myBelt;
    
    public Passenger(String id, ConveyorBelt belt) {
        this.id = id;
        this.myBelt = belt;
    }
    
    @Override
    public void run() {
            long t1 = (new Date()).getTime();
            for(int i = 0; i < 2; i++)
            {      
                try
                {
                    sleep((int)(500+500*Math.random()));
                } catch(InterruptedException e){ }
                    String suitcase = id + "-S"+(i+1); //the name of the suitcase
                    myBelt.leaveCase(id, suitcase, t1);
            }
    }
}
    

