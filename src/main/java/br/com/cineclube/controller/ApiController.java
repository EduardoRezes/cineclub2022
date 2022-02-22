package br.com.cineclube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.cineclube.dao.MovieRepository;
import br.com.cineclube.model.Movie;

/*Controler que escuta as requisições*/
@RestController
public class ApiController {
	//Planejar os endpoints
	// GET /filmes/{id}
	
	// Referencia para o banco de dados = daoFilme | Tudo relacionado a banco está em daoFilme
	@Autowired
	private MovieRepository daoFilme;
	
	@GetMapping("/api/filme/{id}")
	public Movie getFilme(@PathVariable Long id) {
		return daoFilme.getById(id);
	}
	
}
