package pos.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pos.lab.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>, PagingAndSortingRepository<Book, String> {
    void deleteByIsbn(String isbn);

    Optional<Book> findBookByIsbn(String isbn);

    Optional<Book> findBookByGenre(String genre);

    Optional<Book> findBookByTitle(String title);

    Optional<Book> findBookByYear(Integer year);

    Optional<Book> findBookByGenreAndTitle(String genre, String title);

    Optional<Book> findBookByYearAndGenre(Integer year, String genre);

    Optional<Book> findBookByYearAndTitle(Integer year, String title);

}
