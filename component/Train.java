package component;
import tool.Params;

/**
 * Train connects between villages in Concurrencia
 *
 * @author Zheping Liu, 683781, zhepingl@student.unimelb.edu.au
 */

public class Train extends Thread {

    // indicator of being occupied
    private boolean occupied;
    // current group occupying the train
    private Group tourist;
    // current location of the train
    private Location current;
    // start location of the train
    private Location start;
    // destination of the train
    private Location destination;

    public Location getCurrent() {
        return current;
    }

    public Group getTourist() {
        return tourist;
    }

    /**
     * Constructor of the Train
     * @param village1 start location
     * @param village2 destination
     */
    public Train(Location village1, Location village2) {
        this.start = village1;
        this.destination = village2;
        // when train is created, it is at the start location
        this.current = start;
        this.occupied = false;
    }

    @Override
    /**
     * Running the train between start and destination
     */
    public void run() {
        while (!isInterrupted()) {
            if (current.equals(start) && !(start.getLocationId()
                    == Params.VILLAGES/4 && start.isGroupShortType())) {
                try {
                    // if at the start location, and this group is full type
                    // group, ask group in this location to leave and take the
                    // train
                    tourist = start.leaveLocation();

                    // occupy the train
                    occupied = true;

                    // go to the destination
                    current = destination;
                    Thread.sleep(Params.JOURNEY_TIME);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (current.equals(destination)) {
                try {
                    // after arrive at the destination,
                    // drop the group into the location
                    destination.enterLocation(tourist);

                    // empty the train
                    tourist = null;
                    occupied = false;

                    // back to the start location
                    current = start;
                    Thread.sleep(Params.JOURNEY_TIME);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
