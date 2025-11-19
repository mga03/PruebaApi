package pruebaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

// 1. Especifica dónde están tus Repositorios
@EnableJpaRepositories(basePackages = "pruebaapi.repository")
// 2. Especifica dónde están tus Modelos/Entidades
@EntityScan(basePackages = "pruebaapi.model")
// 3. El escaneo base de Spring Boot para Controllers y Services
@SpringBootApplication
public class PruebaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApiApplication.class, args);
	}

}