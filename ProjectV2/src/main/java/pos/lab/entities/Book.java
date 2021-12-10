package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {
    @Id
    @Column(name = "isbn", nullable = false, length = 45)
    private String isbn;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "title", nullable = false, length = 45)
    private String title;
    @Column(name = "genre", nullable = false, length = 45)
    private String genre;
    private String edition;

    @OneToMany(
            mappedBy = "bookIsbn",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BookAuthor> authors;

    public Book() {
    }

    public Book(Integer year, String title, String genre, String edition) {
        this.year = year;
        this.title = title;
        this.genre = genre;
        this.edition = edition;
        authors = new HashSet<>();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getYear(), book.getYear()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getGenre(), book.getGenre()) && Objects.equals(getEdition(), book.getEdition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getYear(), getTitle(), getGenre(), getEdition());
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", edition='" + edition + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }
}
