package br.com.alura.leitura;

import br.com.alura.leitura.controller.LeituraController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeituraApplication implements CommandLineRunner {

	@Autowired
	private LeituraController leituraController;

	public static void main(String[] args) {
		SpringApplication.run(LeituraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		leituraController.iniciarAplicacao();
	}
}