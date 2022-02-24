package br.com.cineclube.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cineclube.dao.MovieRepository;
import br.com.cineclube.model.Category;
import br.com.cineclube.model.Movie;

@Controller
@RequestMapping("/filmes")
public class MovieController {

	@Autowired
	private MovieRepository dao;
	
	
	@PostMapping("/save")
	public String save(@Valid Movie movie, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "movies/manter.html";
		}
		dao.save(movie);
		return "redirect:/filmes/list";
	}
	
	@RequestMapping("/new")
	public String newForm(Model model) {
		
		// objeto filme sera mapeado ao ${filme} da view
		Movie filme = new Movie();
		//lembre-se de que o apelido dado abaixo tem que ser o mesmo da view
		//                  Apelido
		model.addAttribute("filme", filme);
		
		// cria lista de categorias
		model.addAttribute("categories", Category.values());
		return "movies/manter.html";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {
		dao.deleteById(id);
		return "redirect:/filmes/list";
	}
	
	@RequestMapping("/list")
	public String home(Model model) {
		List<Movie> listMovies = dao.findAll();
		model.addAttribute("listMovies", listMovies);
		model.addAttribute("categories", Category.values());
		return "movies/listMovie.html";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Movie movie = dao.getById(id);
		model.addAttribute("filme", movie);		
		model.addAttribute("categories", Category.values());
		return "movies/manter.html";
	}
}
