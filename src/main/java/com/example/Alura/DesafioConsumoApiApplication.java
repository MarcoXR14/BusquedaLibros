package com.example.Alura;

import com.example.Alura.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioConsumoApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioConsumoApiApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.muestraMenu();
    }
}
