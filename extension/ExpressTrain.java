package extension;

import component.Group;
import component.Location;
import component.Train;
import tool.Params;

/**
 * Express train between k/4 and 3k/4 villages in the Extension task 1.
 */
public class ExpressTrain extends Train {

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

    /**
     * Constructor of the Train
     * @param village1 start location
     * @param village2 destination
     */
    public ExpressTrain(Location village1, Location village2) {
        super(village1, village2);
        this.start = village1;
        this.destination = village2;
        this.current = start;
        this.occupied = false;
    }

    /**
     * Running the train between start and destination
     */
    public void run() {
        while (!isInterrupted()) {
            if (current.equals(start) && start.isGroupShortType()) {
                try {
                    synchronized (destination) {
                        while (destination.isOccupied()) {
                            destination.wait();
                        }
                    }

                    // if at the start location, ask group in this
                    // location to leave and take the train
                    tourist = start.leaveLocation(start.isGroupShortType());

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
