package local;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Paso. Garantiza una buena respuesta a las órdenes dadas por los botones 
 * de la interfaz.
 * @author jorge
 */
public class Pass {
    
    //Variables
    Lock lock = new ReentrantLock();
    private final Condition employee1 = lock.newCondition();
    private final Condition employee2 = lock.newCondition();
    private final Condition global = lock.newCondition();
    private boolean emp1, emp2, glo = false;
    
    /**
     * Constructor vacío.
     */
    public Pass() {
    }
    
    /**
     * Se comprueba quién ha recibido una orden (Jorge, Dani o todos).
     * @param name Nombre de quien esta comprobando su estado.
     */
    public void watch(String name) {
        try {
            lock.lock();
            while(name.contains("Jorge") && emp1) {
                emp1=false;     
                try {
                    employee1.await();
                } catch (InterruptedException e) {}
            }
            while(name.contains("Dani") && emp2) {
                emp2=false;
                try {
                    employee2.await();
                } catch (InterruptedException e) {}
            }
            while(glo) {
                glo=false;
                try {
                    global.await();
                } catch (InterruptedException e) {}
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Dependiendo del botón que se haya pulsado, continuará Jorge, 
     * Dani o todos.
     * @param name el botón pulsado.
     */
    public void resume(String name) {
        try {
            lock.lock();
            if(name.equals("jButton2")) {
                emp1=false;
                employee1.signal();
            }
            if(name.equals("jButton3")) {
                emp2=false;
                employee2.signal();
            }
            if(name.equals("jButton1")) {
                glo=false;
                global.signal();
            }
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * Dependiendo del botón que se haya pulsado. Parará Jorge,
     * Dani o todos.
     * @param button el botón pulsado.
     */
    public void stop(String button) {
        try {
            lock.lock();
            if(button.equals("jButton2")) {
                emp1=true;
            }
            if(button.equals("jButton3")) {
                emp2=true;
            }
            if(button.equals("jButton1")) {
                glo=true;
            }           
        } finally {
            lock.unlock();
        }
    }
}
