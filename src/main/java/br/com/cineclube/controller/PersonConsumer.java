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

import br.com.cineclube.model.Person;
import br.com.cineclube.model.WrapperPersonSearch;

@RestController
@RequestMapping("/api/p1")
public class PersonConsumer {

	@Value("${api.moviedb.key}")
	private String apiKey;

	@Autowired
	private RestTemplate apiRequest;

	@RequestMapping("/person/{id}")
	public Person getById(@PathVariable Long id) {
		String personUrl = "http https://api.themoviedb.org/3/person/" + id + "?api_key=" + apiKey;
		Person person = apiRequest.getForObject(personUrl, Person.class);
		return person;
	}

	@GetMapping("/search")
	public WrapperPersonSearch searchPerson(@RequestParam String name) {

		String personUrl = "https://api.themoviedb.org/3/search/person?api_key=${apiKey}&query=${name}";

		return apiRequest.getForObject(personUrl, WrapperPersonSearch.class);
	}

	@GetMapping("/personTMDB")
	public Optional<Person> searchByName(@RequestParam String name) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("id", "id");
		params.put("name", name);
		params.put("popularity", "popularity");
	    params.put("profile_path", "profile_path");
		String personUrl = "https://api.themoviedb.org/3/search/person?api_key={key}&query={query}&popularity={popularity}&profile_path={profile_path}";
		WrapperPersonSearch searchResult = apiRequest.getForObject(personUrl, WrapperPersonSearch.class, params);
 
		Person person = null;
		if (searchResult.getResults()!=null) {
			person = searchResult.getResults().get(0);
		}
		//assert searchResult != null;
		return Optional.ofNullable(person);
	}
}
