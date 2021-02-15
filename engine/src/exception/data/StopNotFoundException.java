package exception.data;

public class StopNotFoundException extends TransPoolDataException {
    private String message;

    public StopNotFoundException(String stopName) {
        message = "The stop " + stopName + " was not found.";
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
