package exception.data;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;

/**
 * Thrown when a searched path was not found.
 */
public class PathDoesNotExistException extends TransPoolDataException {
    private String message;

    public PathDoesNotExistException(String source, String destination) {
        message = "There is no such path from " + source + " to " + destination + ".";
    }

    public PathDoesNotExistException(Stop source, Stop destination) {
        message = "There is no such path from " + source.getName() + " to " + destination.getName() + ".";
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
