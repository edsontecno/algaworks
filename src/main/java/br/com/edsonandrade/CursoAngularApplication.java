package br.com.edsonandrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.edsonandrade.config.property.AlgamoneyApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyApiProperty.class)
public class CursoAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoAngularApplication.class, args);
	}
}
