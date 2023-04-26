package hw2;


/**
* Implementation of the Fork object in the Dining Philosophers problem 
*
* @author Zachary Wenzel
*/

public class Fork {
    private int id;
    private boolean availableFlag = true;

    private final String pickUpMsg = " is not available";
    private final String putDownMsg = " is not being held";

    public Fork(int id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return availableFlag;
    }

    public void pickup() {
        if(!availableFlag) {
            throw new IllegalStateException(toString() + pickUpMsg);
        }
        availableFlag = false;
    }

    public void putDown() {
        if (availableFlag) {
            throw new IllegalStateException(toString() + putDownMsg);
        }
        availableFlag = true;
    }

    public String toString() {
        return "Fork " + id;
    }
}