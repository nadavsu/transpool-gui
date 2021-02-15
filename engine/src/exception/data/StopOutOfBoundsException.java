package exception.data;

/**
 * Thrown when trying to create a stop which is out of bounds.
 */
public class StopOutOfBoundsException extends TransPoolDataException {
    private String message;

    public StopOutOfBoundsException(String stopName) {
        this.message = "The stop " + stopName + " is out of the map bounds.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
