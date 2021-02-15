package exception.data;

/**
 * Thrown when trying to create a new stop with coordinates that already taken.
 */
public class StopCoordinatesDuplicationException extends TransPoolDataException {
    private String message;

    public StopCoordinatesDuplicationException(int x, int y) {
        message = "There are two or more stops with the coordinates (" + x + "," + y + ").";
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
