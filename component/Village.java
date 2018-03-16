package component;
import tool.Params;
import tool.Print;

/**
 * Village in the Concurrencia
 *
 * @author Zheping Liu, 683781, zhepingl@student.unimelb.edu.au
 */

public class Village implements Location {

    // the id of the village
    private int villageId;
    // current group occupying the village
    private volatile Group tourist;
    // indicator of being occupied
    private volatile boolean occupied;
    // next location after this village
    private Location nextLocation;

    @Override
    /**
     * EXTENSION : check if the current group is short type or not
     * @return true if group is short type
     */
    public synchronized boolean isGroupShortType() {
        while (tourist == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return tourist.isShort();
    }

    /**
     * Constructor of the Village
     * @param i given id of the village
     */
    public Village(int i) {
        this.villageId = i;
        this.occupied = false;
        this.nextLocation = null;
    }

    /**
     * Assign the next location after the village
     * @param nextLocation the next location after the village
     */
    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    public synchronized int getVillageId() {
        return villageId;
    }

    public synchronized Group getTourist() {
        return tourist;
    }

    @Override
    /**
     * Check if the village is being occupied
     */
    public synchronized boolean isOccupied() {
        return occupied;
    }

    @Override
    /**
     * Ask the group in this village to leave
     * @return the group leaving the village
     */
    public synchronized Group leaveLocation() {
        // lock the next location
        synchronized (nextLocation) {
            // wait until the next location is not occupied
            while (nextLocation.isOccupied()) {
                try {
                    nextLocation.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // wait until this location is occupied
        while (!occupied) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Group temp = tourist;
        // empty the village
        tourist = null;
        occupied = false;
        // notify all threads that are waiting
        // on the availability of this village
        notifyAll();
        // Print the message
        Print.printLeaveVillage(temp.getId(), villageId);
        return temp;
    }

    /**
     * EXTENSION : ask the group in this village to leave
     * @param shortType indicator if the group is short type
     * @return the group leaving the village
     */
    public synchronized Group leaveLocation(boolean shortType) {
        if (!shortType) {
            return leaveLocation();
        } else {
            // wait until this location is occupied
            while (!occupied) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Group temp = tourist;
            // empty the village
            tourist = null;
            occupied = false;
            // notify all threads that are waiting
            // on the availability of this village
            notifyAll();
            // Print the message
            Print.printLeaveVillageExpress(temp.getId(), villageId);
            return temp;
        }
    }

    @Override
    public int getLocationId() {
        return getVillageId();
    }

    /**
     * Ask the group in the train to enter this village
     * @param tourist the group is entering the village
     */
    public synchronized void enterLocation(Group tourist) {
        // wait until this village is not occupied
        while (occupied) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // occupy the village
        this.tourist = tourist;
        this.occupied = true;
        // print the message
        Print.printEnterVillage(tourist.getId(), villageId);
        // notify all threads that are waiting this village to be occupied
        notifyAll();
    }

    /**
     * Village to String
     * @return Village + id
     */
    public String toString() {
        return "Village " + villageId;
    }

}
