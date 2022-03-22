package br.com.cineclube.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.model.Movie;
import br.com.cineclube.model.WrapperMovieSearch;

@RestController
@RequestMapping("/api/v1")
public class MovieConsumer {
	
	@Value("${api.moviedb.key}")
    private String apiKey;

    @Autowired
    private RestTemplate apiRequest;
    
    @RequestMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable Long id) {
    	String movieUrl = 
        		"https://api.themoviedb.org/3/movie/" + id + "?api_key=" +  apiKey;
        Movie movie = apiRequest.getForObject(movieUrl, Movie.class);
        return movie;
    }
    
    @GetMapping("/search")
	public WrapperMovieSearch searchMovie(@RequestParam String title, @RequestParam String year) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query", title);
		params.put("year", year);
		params.put("lang", "en-US");
		String movieUrl = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}&year={year}&language={lang}";
		WrapperMovieSearch res = apiRequest.getForObject(movieUrl, WrapperMovieSearch.class, params);
		return res;
	}
    
    @GetMapping("/movie")
	public Optional<Movie> searchByName(@RequestParam String title, @RequestParam String year) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query", title);
		params.put("year", year);
		params.put("lang", "en-US");
		String movieUrl = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}&year={year}&language={lang}";
		WrapperMovieSearch res = apiRequest.getForObject(movieUrl, WrapperMovieSearch.class, params);
		
		Movie movie = null;
		if (res.getResults()!=null && res.getResults().size()>0)
			movie = res.getResults().get(0);
		return Optional.ofNullable(movie);
	}
}