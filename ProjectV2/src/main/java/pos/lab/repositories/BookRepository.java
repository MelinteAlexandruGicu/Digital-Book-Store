package pos.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.lab.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    void deleteByIsbn(String isbn);

    Optional<Book> findBookByIsbn(String isbn);

    Optional<List<Book>> findBookByGenre(String genre);

    Optional<List<Book>> findBookByTitle(String title);

    Optional<List<Book>> findBookByYear(Integer year);

    Optional<List<Book>> findBookByGenreAndTitle(String genre, String title);

    Optional<List<Book>> findBookByYearAndGenre(Integer year, String genre);

    Optional<List<Book>> findBookByYearAndTitle(Integer year, String title);

}
