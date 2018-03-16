package tool;
import java.util.Random;

/**
 * Parameters that influence the behaviour of the system.
 * 
 * @author ngeard@unimelb.edu.au
 *
 */

public class Params {

  //EXTENSION : the number of villages the extension needs
  public final static int EXTENSION_REQUIREMENT = 8;

  //the number of villages
  //public final static int VILLAGES = 6;
  public final static int VILLAGES = 8;

  //the time interval at which main.Main checks threads are alive
  public final static int MAIN_INTERVAL = 50;

  //the time it takes to operate the cable car
  public final static int OPERATE_TIME = 800;

  //the time it takes for a single train journey
  public final static int JOURNEY_TIME = 1200;

  //the maximum amount of time between group arrivals
  public final static int MAX_ARRIVE_INTERVAL = 2400;

  //the maximum amount of time between group departures
  public final static int MAX_DEPART_INTERVAL = 800;

  //the maximum amount of time between operating the cable car
  public final static int MAX_OPERATE_INTERVAL = 6400;

/**
 * For simplicity, we assume uniformly distributed time lapses.
 * An exponential distribution might be a fairer assumption.
 */

  public static int arrivalLapse() {
    Random random = new Random();
    return random.nextInt(MAX_ARRIVE_INTERVAL);
  }

  public static int departureLapse() {
    Random random = new Random();
    return random.nextInt(MAX_DEPART_INTERVAL);
  }

  public static int operateLapse() {
    Random random = new Random();
    return random.nextInt(MAX_OPERATE_INTERVAL);
  }
}

