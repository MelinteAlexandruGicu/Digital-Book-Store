package pos.lab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pos.lab.advices.BookAuthorNotFoundAdvice;
import pos.lab.entities.Author;
import pos.lab.entities.Book;

import pos.lab.exceptions.AuthorNotFoundException;
import pos.lab.exceptions.BookAuthorNotFoundException;
import pos.lab.exceptions.BookNotFoundException;
import pos.lab.repositories.AuthorRepository;
import pos.lab.repositories.BookAuthorRepository;
import pos.lab.repositories.BookRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ABCService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;

    @Autowired
    public ABCService(BookRepository bookRepository, AuthorRepository authorRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookAuthorRepository = bookAuthorRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public List<Book> findBookByGenre(String genre) {
        return bookRepository.findBookByGenre(genre).orElseThrow(() -> new BookNotFoundException("Book by genre " + genre + " was not found!"));
    }

    public List<Book> findBookByGenreAndTitle(String genre, String title) {
        return bookRepository.findBookByGenreAndTitle(genre, title).orElseThrow(() -> new BookNotFoundException("Book by genre " + genre + " and title " + title + " was not found!"));
    }

    public List<Book> findBookByYearAndGenre(Integer year, String genre) {
        return bookRepository.findBookByYearAndGenre(year, genre).orElseThrow(() -> new BookNotFoundException("Book by genre " + genre + " and year " + year + " was not found!"));
    }

    public List<Book> findBookByYearAndTitle(Integer year, String title) {
        return bookRepository.findBookByYearAndTitle(year, title).orElseThrow(() -> new BookNotFoundException("Book by genre " + title + " and year " + year + " was not found!"));
    }

    public  List<Book> findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title).orElseThrow(() -> new BookNotFoundException("Book by edition " + title + " was not found!"));
    }

    public List<Book> findBookByYear(Integer year) {
        return bookRepository.findBookByYear(year).orElseThrow(() -> new BookNotFoundException("Book by year " + year + " was not found!"));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book newBook, String isbn) {
        return bookRepository.findBookByIsbn(isbn).map(book -> {
            book.setYear(newBook.getYear());
            book.setTitle(newBook.getTitle());
            book.setGenre(newBook.getGenre());
            book.setEdition(newBook.getEdition());
            return addBook(book);
        }).orElseGet(() -> {
            newBook.setIsbn(isbn);
            return addBook(newBook);
        });
    }

    public void deleteByIsbn(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Author by id " + id + " was not found!"));
    }

    public List<Author> findAuthorByFirstname(String firstname) {
        return authorRepository.findByFirstname(firstname).orElseThrow(() -> new AuthorNotFoundException("Author by firstname " + firstname + " was not found!"));
    }

    public List<Author> findAuthorByLastname(String lastname) {
        return authorRepository.findByLastname(lastname).orElseThrow(() -> new AuthorNotFoundException("Author by lastname " + lastname + " was not found!"));
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author newAuthor, Integer id) {
        return authorRepository.findById(id).map(author -> {
            author.setFirstname(newAuthor.getFirstname());
            author.setLastname(newAuthor.getLastname());
            return addAuthor(author);
        }).orElseGet(() -> {
            newAuthor.setId(id);
            return addAuthor(newAuthor);
        });
    }

    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }

    public List<Author> findAllBooksAuthors(String bookIsbn) {
        if(bookAuthorRepository.findByBookIsbnIsbn(bookIsbn).isPresent()) {
            Optional<List<Author>> authors = authorRepository.findAllByBookAuthorsIn(bookAuthorRepository.findByBookIsbnIsbn(bookIsbn).get());
            return authors.orElseThrow(() -> new BookAuthorNotFoundException("BookAuthor by bookIsbn " + bookIsbn + " was not found!"));
        }
        else
            return null;
    }




    ///////////////////////////////////////////////////////////////////////////////////////////////////





}
