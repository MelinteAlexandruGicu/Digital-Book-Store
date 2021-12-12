package pos.lab.exceptions;

public class BookAuthorNotFoundException extends RuntimeException {
    public BookAuthorNotFoundException(String id) {
        super(String.valueOf(id));
    }
}
