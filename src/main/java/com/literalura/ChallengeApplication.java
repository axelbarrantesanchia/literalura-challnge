package com.literalura;

import com.literalura.principal.Principal;
import com.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository);
		principal.muestraElMenu();

	}

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
