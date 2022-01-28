package pos.project.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pos.project.entities.Authority;
import pos.project.entities.User;
import pos.project.repositories.AuthorityRepository;
import pos.project.repositories.UserDetailsRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerUserServiceImpl implements CustomUserService, UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepository.findByUsername(username);
        if(user == null) {
            log.error("User not found in db!");
            throw new UsernameNotFoundException("User not found in db!");
        } else {
            log.info("User {} found in db!", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to db", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDetailsRepository.save(user);
    }

    @Override
    public Authority saveAuthority(Authority authority) {
        log.info("Saving new authority {} to db", authority.getAuthority());
        return authorityRepository.save(authority);
    }

    @Override
    public void addAuthorityToUser(String username, String authorityName) {
        log.info("Adding authority {} to user {} to the database", authorityName, username);
        User user = userDetailsRepository.findByUsername(username);
        Authority authority = authorityRepository.findByAuthority(authorityName);
        user.getAuthorities().add(authority);
    }

    @Override
    public User getUser(String username) {
        log.info("Find the user {}", username);
        return userDetailsRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Find all users");
        return userDetailsRepository.findAll();
    }


}
