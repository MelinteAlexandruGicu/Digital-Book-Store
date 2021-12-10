package pos.lab.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String id) {
        super(String.valueOf(id));
    }
}
