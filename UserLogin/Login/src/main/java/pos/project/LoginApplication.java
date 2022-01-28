package pos.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pos.project.entities.Authority;
import pos.project.entities.User;
import pos.project.services.CustomUserService;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class LoginApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(CustomUserService customUserService) {
		return args -> {
			customUserService.saveAuthority(new Authority(null, "AUTHORITY_USER"));
			customUserService.saveAuthority(new Authority(null, "AUTHORITY_ADMIN"));

			customUserService.saveUser(new User(null, "alexmelinte", "parola1", new Date(), null, "Alexandru", "Melinte", "alex.melinte@yahoo.com", true, new ArrayList<>()));
			customUserService.saveUser(new User(null, "stefisopca", "parola2", new Date(), null, "Stefania", "Sopca", "stefania.sopca@yahoo.com", true, new ArrayList<>()));
			customUserService.saveUser(new User(null, "andreidima", "parola3", new Date(), null, "Andrei", "Dima", "andrei.dima@yahoo.com", true, new ArrayList<>()));

			customUserService.addAuthorityToUser("alexmelinte", "AUTHORITY_ADMIN");
			customUserService.addAuthorityToUser("stefisopca", "AUTHORITY_USER");
			customUserService.addAuthorityToUser("andreidima", "AUTHORITY_USER");
		};
	}
}
