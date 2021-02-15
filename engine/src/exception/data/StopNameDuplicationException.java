package exception.data;

/**
 * Thrown when trying to create a stop with a name that is already used.
 */
public class StopNameDuplicationException extends TransPoolDataException {
    private String message;

    public StopNameDuplicationException(String stopName) {
        message = "There are two or more stops named " + stopName + ".";
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
