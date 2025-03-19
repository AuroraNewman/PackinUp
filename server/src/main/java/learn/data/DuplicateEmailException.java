package learn.data;

public class DuplicateEmailException extends RuntimeException {
    String message = "Email already exists.";
    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
