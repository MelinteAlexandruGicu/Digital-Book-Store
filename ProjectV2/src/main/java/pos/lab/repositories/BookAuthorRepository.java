package pos.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pos.lab.entities.Author;
import pos.lab.entities.BookAuthor;
import pos.lab.entities.BookAuthorId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {
    Set<BookAuthor> findByBookIsbnIsbn(String bookIsbn);
}
