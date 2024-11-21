package com.alura.literalura;

import com.alura.literalura.service.ConsumoApiService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);

		SpringApplication.run(LiteraluraApplication.class, args);



	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApiService consumoApiService = new ConsumoApiService();
		String json =  consumoApiService.obtenerDatos("https://gutendex.com/books/?page=2");
		System.out.println(json);
	}

}
