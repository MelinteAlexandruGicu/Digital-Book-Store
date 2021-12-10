package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book_has_author")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId bookAuthorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("book_isbn")
    @JoinColumn(name = "isbn")
    private Book bookIsbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("author_id")
    @JoinColumn(name = "id")
    private Author authorId;

    @Column(name = "indexx")
    private Integer indexx;

    public BookAuthor() {
    }

    public BookAuthor(Book bookIsbn, Author authorId, Integer indexx) {
        this.bookIsbn = bookIsbn;
        this.authorId = authorId;
        this.indexx = indexx;
        this.bookAuthorId = new BookAuthorId(bookIsbn.getIsbn(), authorId.getId());
    }

    public BookAuthorId getBookAuthorId() {
        return bookAuthorId;
    }

    public void setBookAuthorId(BookAuthorId bookAuthorId) {
        this.bookAuthorId = bookAuthorId;
    }

    public Book getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(Book bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public Integer getIndexx() {
        return indexx;
    }

    public void setIndexx(Integer indexx) {
        this.indexx = indexx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthor)) return false;
        BookAuthor that = (BookAuthor) o;
        return Objects.equals(getBookAuthorId(), that.getBookAuthorId()) && Objects.equals(getBookIsbn(), that.getBookIsbn()) && Objects.equals(getAuthorId(), that.getAuthorId()) && Objects.equals(getIndexx(), that.getIndexx());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookAuthorId(), getBookIsbn(), getAuthorId(), getIndexx());
    }
}
