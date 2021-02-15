package exception.data;

/**
 * Master class for all file exceptions.
 */
public class TransPoolDataException extends Exception {
    private final String EXCEPTION_MESSAGE = "Data in the file is invalid!";

    public TransPoolDataException() {

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
