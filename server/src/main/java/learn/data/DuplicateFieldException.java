package learn.data;

public class DuplicateFieldException extends RuntimeException {
    //todo ask pete if i can make this for all duplicate fields in database
    String message = "Field already exists.";
    public DuplicateFieldException(String message) {
        super(message);
    }

    public DuplicateFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
