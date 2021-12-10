package pos.lab.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "author")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "firstname", nullable = false, length = 45)
    private String firstname;
    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;

    @OneToMany(
            mappedBy = "authorId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<BookAuthor> bookAuthors = new HashSet<BookAuthor>();

    public Author() {

    }

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(getId(), author.getId()) && Objects.equals(getFirstname(), author.getFirstname()) && Objects.equals(getLastname(), author.getLastname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
