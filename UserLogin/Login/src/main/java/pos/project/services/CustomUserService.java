package pos.project.services;

import pos.project.entities.Authority;
import pos.project.entities.User;
import java.util.List;

public interface CustomUserService {
   User saveUser(User user);
   Authority saveAuthority(Authority authority);
   void addAuthorityToUser(String username, String authorityName);
   User getUser(String username);
   List<User> getUsers();
}
