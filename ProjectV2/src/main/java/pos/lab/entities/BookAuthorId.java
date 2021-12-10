package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitialized", "handler"})
public class BookAuthorId implements Serializable {
    @NotNull
    private String book_isbn;
    @NotNull
    private Integer author_id;

    public BookAuthorId() {
    }

    public BookAuthorId(String book_isbn, int author_id) {
        this.book_isbn = book_isbn;
        this.author_id = author_id;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookAuthorId)) return false;
        BookAuthorId that = (BookAuthorId) o;
        return Objects.equals(getBook_isbn(), that.getBook_isbn()) && Objects.equals(getAuthor_id(), that.getAuthor_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBook_isbn(), getAuthor_id());
    }
}
