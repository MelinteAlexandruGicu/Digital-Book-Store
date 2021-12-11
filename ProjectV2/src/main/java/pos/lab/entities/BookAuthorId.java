package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitialized", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NotNull
public class BookAuthorId implements Serializable {
    @NotNull
    private String book_isbn;
    @NotNull
    private Integer author_id;

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
