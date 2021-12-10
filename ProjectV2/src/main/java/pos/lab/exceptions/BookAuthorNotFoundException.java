package pos.lab.exceptions;

import pos.lab.entities.BookAuthorId;

public class BookAuthorNotFoundException extends RuntimeException {
    public BookAuthorNotFoundException(String id) {
        super(String.valueOf(id));
    }
}
