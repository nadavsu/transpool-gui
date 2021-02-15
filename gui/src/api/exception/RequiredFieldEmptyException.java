package api.exception;

public class RequiredFieldEmptyException extends Exception {
    private final String EXCEPTION_MESSAGE = "Please fill all required fields.";

    public RequiredFieldEmptyException() {

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
