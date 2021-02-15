package exception;

/**
 * Thrown when no matches were found for a ride request.
 */
public class NoResultsFoundException extends TransPoolRunTimeException {
    private final String EXCEPTION_MESSAGE = "We couldn't find any results for your search :(";

    public NoResultsFoundException() {
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
