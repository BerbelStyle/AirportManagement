package local;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Passage {
    
    Lock lock = new ReentrantLock();
    private final Condition employee1 = lock.newCondition();
    private final Condition employee2 = lock.newCondition();
    private final Condition global = lock.newCondition();
    private boolean emp1, emp2, glo = false;
    private int counter = 0;
    
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
                counter++;
                try {
                    global.await();
                } catch (InterruptedException e) {}
            }
        } finally {
            lock.unlock();
        }
    }
    
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
                while (counter > 0) {
                    counter--;
                    global.signal();
                }
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void stop(String boton) {
        try {
            lock.lock();
            if(boton.equals("jButton2")) {
                emp1=true;
            }
            if(boton.equals("jButton3")) {
                emp2=true;
            }
            if(boton.equals("jButton1")) {
                glo=true;
            }           
        } finally {
            lock.unlock();
        }
    }
}
