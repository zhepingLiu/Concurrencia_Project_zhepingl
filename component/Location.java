package component;

/**
 * The interface that all locations (village and cable car) will implement
 */
public interface Location {
    void setNextLocation(Location nextLocation);
    void enterLocation(Group tourist) throws InterruptedException;
    Group leaveLocation();
    boolean isOccupied();
    String toString();

    // Methods for extension task
    boolean isGroupShortType();
    Group leaveLocation(boolean groupShortType);
    int getLocationId();
}