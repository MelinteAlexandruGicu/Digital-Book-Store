package pos.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pos.project.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(String authority);
}
