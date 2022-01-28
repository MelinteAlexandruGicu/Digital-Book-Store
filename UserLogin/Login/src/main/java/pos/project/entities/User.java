package pos.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private Date dateOfCreation;
    private Date dateOfUpdate;
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Authority> authorities;

}
