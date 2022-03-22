package br.com.cineclube.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cineclube.dao.MovieRepository;
import br.com.cineclube.dao.PersonRepository;
import br.com.cineclube.model.Filme;
import br.com.cineclube.model.Pessoa;

/*Controler que escuta as requisições*/
@RestController
public class ApiController {
	//Planejar os endpoints
	// GET /filmes/{id}
	
	// Referencia para o banco de dados = daoFilme | Tudo relacionado a banco está em daoFilme
	@Autowired
	private MovieRepository daoFilme;
	
	@Autowired
	private PersonRepository daoPerson;
	
	@GetMapping("/api/filme/{id}")
	public Filme getFilme(@PathVariable Long id) {
		return daoFilme.getById(id);
	}
	
	
	/*=====================================Person Area=====================================*/
	@GetMapping("/api/pessoa/{id}")
	Optional<Pessoa> getPerson(@PathVariable Long id) {
		return daoPerson.findById(id);
	}
	
	@GetMapping(value = "/api/pessoas")
	Iterable<Pessoa> getAllPerson(){
		return daoPerson.findAll();
	}
	
	@PostMapping("/api/pessoa")
	Pessoa postPerson(@RequestBody Pessoa pessoa){
		daoPerson.save(pessoa);
		return pessoa;
	}
	
	@DeleteMapping("/api/pessoa/{id}")
	void deletePerson(@PathVariable Long id) {
		daoPerson.deleteById(id);
	}
	
	@PutMapping("/api/pessoa/{id}")
	ResponseEntity<Pessoa> putPerson(@PathVariable Long id, @RequestBody Pessoa pessoa){
		Pessoa p = daoPerson.save(pessoa);
		if(p != null) {
			return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}
}
