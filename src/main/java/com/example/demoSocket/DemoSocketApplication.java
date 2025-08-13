package com.example.demoSocket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoSocketApplication {

	public static void main(String[] args) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = "123";
			String hashedPassword = encoder.encode(rawPassword);
			System.out.println("Hashed: " + hashedPassword);

		SpringApplication.run(DemoSocketApplication.class, args);
	}

	@Bean
	public CommandLineRunner printAuthProviders(AuthenticationManager authenticationManager) {
		return args -> {
			if (authenticationManager instanceof ProviderManager) {
				ProviderManager pm = (ProviderManager) authenticationManager;
				System.out.println("Registered AuthenticationProviders:");
				pm.getProviders().forEach(p -> System.out.println(" - " + p.getClass().getName()));
			} else {
				System.out.println("AuthenticationManager is not a ProviderManager");
			}
		};
	}

}
