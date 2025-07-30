package com.example.demoSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
