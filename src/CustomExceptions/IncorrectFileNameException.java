package CustomExceptions;

public class IncorrectFileNameException extends RuntimeException {
    public IncorrectFileNameException(String message) {
        super(message);
    }
}
