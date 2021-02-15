package exception.time;

import exception.data.TransPoolDataException;

public class InvalidTimeException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "Time is not valid! Must be in the range of 00:00 to 23:59";

    public InvalidTimeException() {
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
