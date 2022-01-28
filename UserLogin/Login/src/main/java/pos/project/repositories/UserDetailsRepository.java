package pos.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.project.entities.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
