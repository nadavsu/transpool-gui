package exception.data;

import data.transpool.TransPoolData;
import data.transpool.user.TransPoolDriver;

public class InvalidDayStartException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "Day must be bigger than 0";

    public InvalidDayStartException() {
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
