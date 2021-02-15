package exception.time;

public class InvalidHoursException extends InvalidTimeException {
    private final String EXCEPTION_MESSAGE = "Hour is not valid! Must be in the range of 0 - 23";

    public InvalidHoursException() {
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
