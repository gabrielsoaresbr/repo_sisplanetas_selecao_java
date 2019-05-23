package com.provacredilink.sisplanetasapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.provacredilink.sisplanetasapi.model.Planeta;
import com.provacredilink.sisplanetasapi.repository.PlanetaRepository;

@SpringBootApplication
public class SisplanetasApiApplication implements CommandLineRunner  {
	
	@Autowired
	private PlanetaRepository planetaRepository;

	public static void main(String[] args) {
		SpringApplication.run(SisplanetasApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		planetaRepository.deleteAll();

		planetaRepository.save(new Planeta("1", "Naboo", "Tropical", "Mar"));
		System.out.println("Criado planeta Naboo");
		planetaRepository.save(new Planeta("2", "Tatooine", "Deserto", "Arenoso"));
		System.out.println("Criado planeta Tatooine");
		planetaRepository.save(new Planeta("3", "Hoth", "Polar", "Congelado"));
		System.out.println("Criado planeta Hoth");
		
		
	}

}
