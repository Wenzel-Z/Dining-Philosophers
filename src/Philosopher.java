package hw2;
import java.util.Random;

/**
* Implementation of the Philosopher class in the Dining Philosophers problem 
*
* @author Zachary Wenzel
*/

 public class Philosopher implements Runnable{
    private String name;
    private Object waiter;
    private Fork leftFork;
    private Fork rightFork;

    private long estTotalTime;
    private long forkTime = 0;

    private int thoughts = 0;
    private int eats = 0;

    boolean hasForks = false;

    public Philosopher(String name, Object waiter, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.waiter = waiter;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
     }
     
    boolean stopFlag = false;
    Random random = new Random();

    public Boolean isStopping() {
        return stopFlag;
    }

    public Boolean setStopFlag(boolean flag) {
        stopFlag = flag;
        return stopFlag;
    }

    public String getId() {
        return this.name;
    }

    public int getThoughts() {
        return thoughts;
    }

    public int getEats() {
        return eats;
    }

    public long getForkTime() {
        return this.forkTime;
    }

    public long getTotalTime() {
        return estTotalTime;
    }

     public void run() {
        long firstTime = 0;
        long startTotalTime = System.nanoTime();

        while (!isStopping()) {
            thoughts++;
            long thinkTime = (long)(random.nextFloat() * 10.0);
            
            try {
                Thread.sleep(thinkTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            firstTime = System.nanoTime(); 
            while(!hasForks) {
                try {
                    synchronized(waiter) {
                        if (leftFork.isAvailable() && rightFork.isAvailable()) {
                            leftFork.pickup();
                            rightFork.pickup();
                            hasForks = true;
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                } 
            }

            forkTime += System.nanoTime() - firstTime;

            eats++;
            long eatTime = (long)(random.nextFloat() * 10.0);

            try {
                Thread.sleep(eatTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                leftFork.putDown();
                rightFork.putDown();
                hasForks = false;
            } catch(Exception e) {
                e.printStackTrace();
                }
            }
        estTotalTime = System.nanoTime() - startTotalTime;
     }
 }