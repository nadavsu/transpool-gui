package exception.data;

import exception.data.TransPoolDataException;

public class InvalidRouteException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "A route must have two or more stops";

    public InvalidRouteException() {
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
