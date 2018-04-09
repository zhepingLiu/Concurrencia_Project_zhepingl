package component;
import tool.Params;
import tool.Print;

/**
 * CableCar that takes people entering and leaving the Concurrencia
 * Singleton Class
 *
 * @author Zheping Liu, 683781, zhepingl@student.unimelb.edu.au
 */
public class CableCar implements Location {

    // EXTENSION : the id of cable car location
    private static final int CABLE_CAR_ID = -1;
    // static instance of this class
    private static CableCar cableCar;
    // indicator of being occupied
    private volatile boolean occupied;
    // current group occupying the cable car
    private volatile Group tourist;
    // next location after the cable car
    private Location nextLocation;
    // enum indicates two locations for the cable car
    private enum currentLocation {
        TERMINUS,
        VALLEY
    }
    // current location of the cable car
    private volatile currentLocation current;

    /**
     * Constructor of the cable car
     */
    private CableCar() {
        this.occupied = false;
        this.current = currentLocation.VALLEY;
        this.nextLocation = null;
    }

    /**
     * Get the instance of the cable car
     * @return the instance of the cable car
     */
    public static CableCar getCableCar() {
        if (cableCar == null) {
            // if cableCar is not initialised, initialising it
            cableCar = new CableCar();
        }
        return cableCar;
    }

    /**
     * Getter of current location of cable car
     * @return current location of cable car
     */
    public synchronized currentLocation getCurrent() {
        return current;
    }

    /**
     * Getter of current group on the cable car
     * @return current group on the cable car
     */
    public synchronized Group getTourist() {
        return tourist;
    }

    /**
     * Assign the next location after the cable car
     * @param nextLocation the next location after the cable car
     */
    public synchronized void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    /**
     * Check if it is at the Valley
     * @return boolean
     */
    public synchronized boolean isAtValley() {
        return current == currentLocation.VALLEY;
    }

    /**
     * Check if it is at the Terminus
     * @return boolean
     */
    public synchronized boolean isAtTerminus() {
        return current == currentLocation.TERMINUS;
    }

    /**
     * Operator operating the cable car
     */
    public synchronized void autoOperate() {
        // wait until the cable car is not occupied
        while (occupied) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // operate the cable car to the other location
        operate();
    }

    /**
     * Operate the cable car to the other location
     */
    public synchronized void operate() {

        if (current.equals(currentLocation.TERMINUS)) {
            // if the cable car is currently at Terminus
            // move it to the Valley
            current = currentLocation.VALLEY;

            // if it is occupied, print out message with group id
            if (tourist != null) {
                Print.printEnterCableCarDown(tourist.getId());
            }
            else {
                Print.printCableCarDescend();
            }

        } else {
            // if the cable car is currently at Valley
            // move it to the Terminus
            current = currentLocation.TERMINUS;

            if (tourist != null) {
                Print.printEnterCableCarUp(tourist.getId());
            }
            else {
                Print.printCableCarAscend();
            }
        }

        try {
            Thread.sleep(Params.OPERATE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // notifying all threads that are waiting
        // on the location of the cable car
        notifyAll();
    }

    /**
     * Remove a vessel that has returned from the mountain park
     */
    public synchronized void depart() {
        // wait until the cable car is occupied and at the valley
        while (!occupied || !isAtValley()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // assign the current group to temp
        Group temp = tourist;
        // empty the cable car
        tourist = null;
        occupied = false;
        // notify all threads that are waiting on the availability of cable car
        notifyAll();
        // Print out message
        Print.printDepart(temp.getId());
    }

    /**
     * Send a new group to the cable car
     * @param group the group arriving at valley
     */
    public synchronized void arrive(Group group) {
        // wait until the cable car is not occupied and is at valley
        // and next location (village 0) is available
        while (occupied || !isAtValley() || nextLocation.isOccupied()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // occupy the cable car
        tourist = group;
        occupied = true;
        // operate the cable car
        operate();
    }

    @Override
    /**
     * Ask the group in the cable car to leave it
     * @return the Group leaving the cable car
     */
    public synchronized Group leaveLocation() {
        // wait until cable car is occupied and is at Terminus
        while (!occupied || !isAtTerminus()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Group temp = tourist;
        // empty the cable car
        tourist = null;
        occupied = false;
        // notify all threads that waiting on the availability of cable car
        notifyAll();
        // Print the message
        Print.printLeaveCableCar(temp.getId());
        return temp;
    }

    @Override
    /**
     * Ask the group in the train to enter the cable car
     * @param group the group is entering the cable car
     */
    public synchronized void enterLocation(Group group) {
        // wait until the cable car is at Terminus and is not occupied
        while (!isAtTerminus() || occupied) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // occupy the cable car
        tourist = group;
        occupied = true;
        // operate the cable car
        operate();
    }

    @Override
    /**
     * Check if the cable car is occupied
     */
    public synchronized boolean isOccupied() {
        return occupied;
    }

    /**
     * CableCar to String
     * @return "Cable car"
     */
    public String toString() {
        return "Cable car";
    }

    @Override
    /**
     * EXTENSION : check if the current group is short type or not
     * @return true if group is short type
     */
    public synchronized boolean isGroupShortType() {
        if (tourist != null) {
            return tourist.isShort();
        }
        return false;
    }

    @Override
    /**
     * EXTENSION : ask the group in this village to leave.
     * Since cable car will not be connected to any express train
     * the leaveLocation function for short type group is the same
     * as the normal type
     * @param shortType indicator if the group is short type
     * @return the group leaving the village
     */
    public synchronized Group leaveLocation(boolean groupShortType) {
        return leaveLocation();
    }

    @Override
    public int getLocationId() {
        return CABLE_CAR_ID;
    }
}
