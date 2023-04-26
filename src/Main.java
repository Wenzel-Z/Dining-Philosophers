package hw2;

/**
* Implementation of the Dining Philosophers problem 
*
* @author Zachary Wenzel
*/

public class Main {
    public static void main(String[] args) {
        long seconds = Long.parseLong(args[0]);

        String[] names = new String[] {"Dostoyevsky", "Kierkegaard", "Sarte", "Neitzsche", "Beauvoir"};
        Fork[] forks = new Fork[5]; 

        for (int i = 0; i < 5; i++) {
            forks[i] = new Fork(i);
        }
        Object waiter = new Object();

        Philosopher[] phil = new Philosopher[5];
        phil[0] = new Philosopher(names[0], waiter, forks[0], forks[4]);
        for (int i = 1; i < 5; i++) {
            phil[i] = new Philosopher(names[i], waiter, forks[i], forks[i-1]);
        }

        Thread[] th = new Thread[5];
        for (int i = 0; i < 5; i++) {
            th[i] = new Thread(phil[i]);
            System.out.println("Philosopher Id: " + phil[i].getId());
        }

        for (int i = 0; i < 5; i++) {
            th[i].start();
        }
    
        try {
            Thread.sleep(seconds);

            for (int i = 0; i < 5; i++) {
                phil[i].setStopFlag(true);
                th[i].join();
            }

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println("Diner has closed \n");
        
        int count = 0;
        for (int i = 0; i < 5; i++) {
            System.out.println("Philosopher: " + phil[i].getId());
            System.out.println("Thoughts: " + phil[i].getThoughts() + " Meals: " + phil[i].getEats());
            System.out.println("Fork access time (ns): " + phil[i].getForkTime());
            System.out.println("Total time (ns): " + phil[i].getTotalTime());
            
            double totaltime = phil[i].getTotalTime();
            long forktime = phil[i].getForkTime();
            double ratio = forktime/totaltime;

            System.out.println("Ratio: " + (ratio) + " \n");
            if (phil[i].getThoughts() - phil[i].getEats() > 5) {
                count += 1;
            }

        if (count == 5) {
            System.out.println("Sadly, it seems all the philosophers have starved");
            } 
        }
    }
}
