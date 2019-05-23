package com.provacredilink.sisplanetasapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.provacredilink.sisplanetasapi.model.Planeta;
import com.provacredilink.sisplanetasapi.repository.PlanetaRepository;

@RestController
@RequestMapping("api/planetas")
public class PlanetaResource {

	@Autowired
	private PlanetaRepository planetaRepository;

	@PostMapping
	public ResponseEntity<Planeta> criarPlaneta(@RequestBody Planeta planeta, HttpServletResponse response) {
		Planeta planetaSalvo = planetaRepository.save(planeta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(planetaSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(planetaSalvo);
	}

	@GetMapping
	public List<Planeta> listarTodos() {
		return planetaRepository.findAll();
	}

	@GetMapping(path="/nome")
	public ResponseEntity<Planeta> buscarPlanetaPorNome(@PathVariable String nome) {
		Planeta planetaEncontrado = planetaRepository.findByNome(nome);
		return planetaEncontrado != null ? ResponseEntity.ok(planetaEncontrado)
				: ResponseEntity.notFound().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<Planeta> buscarPlanetaPorId(@PathVariable String id) {
		Optional<Planeta> planetaEncontrado = planetaRepository.findById(id);
		return planetaEncontrado.isPresent() ? ResponseEntity.ok(planetaEncontrado.get())
				: ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> removerPlaneta(@PathVariable String id) {
		Optional<Planeta> optPlanetaEncontrado = planetaRepository.findById(id);

		if (optPlanetaEncontrado.isPresent()) {
			Planeta planetaBD = optPlanetaEncontrado.get();
			planetaRepository.delete(planetaBD);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
