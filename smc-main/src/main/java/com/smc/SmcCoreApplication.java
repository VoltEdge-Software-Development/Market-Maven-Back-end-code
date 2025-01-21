package com.smc;

import com.smc.auth.AuthenticationService;
import com.smc.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.smc.entity.user.Role.*;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SmcCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmcCoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			if (!service.isDefaultAvailable()){
				var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email("admin@mail.com")
						.password("1111")
						.role(ADMIN)
						.build();
				System.out.println("Admin token: " + service.register(admin).getAccessToken());

				var manager = RegisterRequest.builder()
						.firstname("John")
						.lastname("Doe")
						.email("user@mail.com")
						.password("1111")
						.role(USER)
						.build();
				System.out.println("User token: " + service.register(manager).getAccessToken());

			}
		};
	}
}
