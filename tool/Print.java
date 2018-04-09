package tool;

/**
 * Print all related messages in specific format
 *
 * @author Zheping Liu, 683781, zhepingl@student.unimelb.edu.au
 */
public class Print {

    // Extension : Print messages state groups enter villages taking express
    public static void printEnterVillageExpress(int groupId, int villageId) {
        System.out.println("[" + groupId + "] enters village " +
                            villageId + " taking express train");
    }
    // Extension : Print messages state groups leave villages taking express
    public static void printLeaveVillageExpress(int groupId, int villageId) {
        System.out.println("[" + groupId + "] leaves village " +
                            villageId + " taking express train");
    }

    // Print messages state groups enter villages
    public static void printEnterVillage(int groupId, int villageId) {
        System.out.println("[" + groupId + "] enters village " + villageId);
    }
    // Print messages state groups leave villages
    public static void printLeaveVillage(int groupId, int villageId) {
        System.out.println("[" + groupId + "] leaves village " + villageId);
    }
    // Print messages state groups enter cable car to go up
    public static void printEnterCableCarUp(int groupId) {
        System.out.println("[" + groupId + "] enters cable car to go up");
    }
    // Print messages state groups enter cable car to go down
    public static void printEnterCableCarDown(int groupId) {
        System.out.println("[" + groupId + "] enters cable car to go down");
    }
    // Print messages state groups leave cable car
    public static void printLeaveCableCar(int groupId) {
        System.out.println("[" + groupId + "] leaves the cable car");
    }
    // Print messages state groups depart
    public static void printDepart(int groupId) {
        System.out.println("[" + groupId + "] departs");
    }
    // Print messages state the cable car ascends
    public static void printCableCarAscend() {
        System.out.println("Cable car ascends");
    }
    // Print messages state the cable car descends
    public static void printCableCarDescend() {
        System.out.println("Cable car descends");
    }
}
