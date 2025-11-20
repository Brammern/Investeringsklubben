package CustomExceptions;

public class InvalidUserIDException extends RuntimeException {
    public InvalidUserIDException(String message) {
        super(message);
    }
}
