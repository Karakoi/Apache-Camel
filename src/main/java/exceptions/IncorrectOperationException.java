package exceptions;

/**
 * Exception for incorrect operation name.
 */
public class IncorrectOperationException extends RuntimeException {


    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public IncorrectOperationException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public IncorrectOperationException(String message) {
        super(message);
    }
}
