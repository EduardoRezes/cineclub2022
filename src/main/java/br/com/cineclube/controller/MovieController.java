package br.com.cineclube.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cineclube.dao.MovieRepository;
import br.com.cineclube.model.Category;
import br.com.cineclube.model.Movie;

@Controller
@RequestMapping("/filmes")
public class MovieController {

	@Autowired
	MovieRepository dao;
	
	@RequestMapping("/save")
	public String save(Model model) {
		Movie avatar = new Movie("Avatar", Category.FICTION, LocalDate.of(2009, 10, 25),new BigDecimal(8.5));
		
		dao.save(avatar);
		List<Movie> listMovies = dao.findAll();
		model.addAttribute("listMovies", listMovies);
		return "movies/listMovie.html";
	}
	
	@RequestMapping("/new")
	public String newForm() {
		return "redirect: /movies/maintain.html";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(Model model) {
		return "index.html";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		Calendar cal = Calendar.getInstance();
		model.addAttribute("today", cal.getTime());
		return "index.html";
	}
}
