package yporque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import yporque.model.Vendedor;
import yporque.repository.VendedorRepository;

import java.util.List;

@SpringBootApplication
@ComponentScan("yporque")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(VendedorRepository vendedorRepository) {
		return (args) -> {
			List<Vendedor> list = vendedorRepository.findByUsername("Administrador");
			if(list.isEmpty()){
				vendedorRepository.save(new Vendedor("Administrador","1qaz2wsx","Administrador","Administrador"));
			}

		};
	}

}
