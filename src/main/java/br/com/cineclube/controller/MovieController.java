package br.com.cineclube.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.dao.MovieRepository;
import br.com.cineclube.model.Category;
import br.com.cineclube.model.Filme;
import br.com.cineclube.model.Movie;
import br.com.cineclube.model.WrapperMovieSearch;

@Controller
@RequestMapping("/filmes")
public class MovieController {

	@Value("${api.moviedb.key}")
    private String apiKey;
	
	@Autowired
    private RestTemplate apiRequest;
	
	@Autowired
	private MovieRepository dao;
	
	
	@PostMapping("/save")
	public String save(@Valid Filme filme, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "movies/manter.html";
		}
		dao.save(filme);
		return "redirect:/filmes/list";
	}
	
	@RequestMapping("/new")
	public String newForm(Model model) {
		
		// objeto filme sera mapeado ao ${filme} da view
		Filme filme = new Filme();
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
		List<Filme> listMovies = dao.findAll();
		model.addAttribute("listMovies", listMovies);
		model.addAttribute("categories", Category.values());
		return "movies/listMovie.html";
	}
	
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Filme filme = dao.getById(id);
		model.addAttribute("filme", filme);		
		model.addAttribute("categories", Category.values());
		return "movies/manter.html";
	}
	
	@GetMapping("/discover/genre")
	public String filme(Model model) {
		Map<String, String> params = new HashMap<>();
		params.put("apikey", apiKey);
		params.put("sort_by", "vote_count.desc");
		params.put("release_date.gte", "1980");
		params.put("release_date.lte", "1990");
		params.put("with_genres", "sci-fi");
		String url = "https://api.themoviedb.org/3/discover/movie?api_key={apikey}&sort_by={sort_by}&release_date.gte={release_date.gte}&release_date.lte={release_date.lte}&with_genres={with_genres}";
		WrapperMovieSearch serMovieSearch = apiRequest.getForObject(url, WrapperMovieSearch.class);
		
		Movie movie = null;
		if (serMovieSearch.getResults()!=null && serMovieSearch.getResults().size()>0)
			movie = serMovieSearch.getResults().get(0);
		model.addAttribute(movie);
				
		return "/movies/listMovieTMDB.html";
	}
}
