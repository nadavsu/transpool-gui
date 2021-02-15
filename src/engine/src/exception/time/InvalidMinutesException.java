package exception.time;

public class InvalidMinutesException extends InvalidTimeException {
    private final String EXCEPTION_MESSAGE = "Minutes are not valid! Must be in the range of 0-59";

    public InvalidMinutesException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    @Override
    public String toString() {
        return EXCEPTION_MESSAGE;
    }
}
