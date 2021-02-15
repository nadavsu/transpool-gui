package exception;

public class TransPoolRunTimeException extends RuntimeException {
    private final String EXCEPTION_MESSAGE = "Aw. Something has went wrong :(";

    public TransPoolRunTimeException() {
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
