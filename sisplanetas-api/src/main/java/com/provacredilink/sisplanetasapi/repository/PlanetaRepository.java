package com.provacredilink.sisplanetasapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.provacredilink.sisplanetasapi.model.Planeta;

public interface PlanetaRepository extends MongoRepository<Planeta, String> {
	
	public Planeta findByNome(String nome);

}
