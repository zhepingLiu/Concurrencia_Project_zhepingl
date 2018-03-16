package main;
import component.CableCar;
import component.Group;
import tool.Params;

/**
 * Produces new tour groups at the base of the mountain park.
 * 
 * @author ngeard@unimelb.edu.au
 *
 */
public class Producer extends Thread {

	// the cable car to which the groups will be sent.
	private CableCar cableCar;
	
	// create a new producer
	public Producer(CableCar cableCar) {
		this.cableCar = cableCar;
	}
	
	// groups are sent to the cable car at random intervals
	public void run() {
		while(!isInterrupted()) {
			try {
				// send a new group to the cable car
				Group group = Group.getNewGroup();
				cableCar.arrive(group);
				
				// wait for the cable car to operate
				sleep(Params.JOURNEY_TIME + Params.OPERATE_TIME);
				
				// let some time pass before the next group arrives
				sleep(Params.arrivalLapse());
			}
			catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}
}
