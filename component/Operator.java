package component;
import tool.Params;

/**
 * Operator controls the cable car when it is not occupied
 *
 * @author Zheping Liu, 683781, zhepingl@student.unimelb.edu.au
 */

public class Operator extends Thread {

    // the cable car it is controlling
    private CableCar cableCar;

    /**
     * Constructor of the Operator
     * @param cableCar the cable car it is controlling
     */
    public Operator(CableCar cableCar) {
        this.cableCar = cableCar;
    }

    @Override
    /**
     * Randomly inspect the cable car, if it is not occupied
     * operate it to the other location
     */
    public void run() {
        while (!isInterrupted()) {
            cableCar.autoOperate();
            try {
                Thread.sleep(Params.operateLapse());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
