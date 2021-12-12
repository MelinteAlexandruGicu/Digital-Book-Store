package pos.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.lab.entities.Author;
import pos.lab.entities.BookAuthor;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAllByBookAuthorsIn(Set<BookAuthor> bookAuthors);

    Optional<Author> findByFirstname(String firstname);

    Optional<Author> findByLastname(String lastname);
}
