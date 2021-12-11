package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book_has_author")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
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

    public BookAuthor(Book bookIsbn, Author authorId, Integer indexx) {
        this.bookIsbn = bookIsbn;
        this.authorId = authorId;
        this.indexx = indexx;
        this.bookAuthorId = new BookAuthorId(bookIsbn.getIsbn(), authorId.getId());
    }

    public BookAuthor(Book bookIsbn, Author authorId) {
        this.bookIsbn = bookIsbn;
        this.authorId = authorId;
        this.bookAuthorId = new BookAuthorId(bookIsbn.getIsbn(), authorId.getId());
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
