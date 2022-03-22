package br.com.cineclube.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.cineclube.dao.PersonRepository;
import br.com.cineclube.model.Person;
import br.com.cineclube.model.Pessoa;
import br.com.cineclube.model.WrapperPersonSearch;

@Controller
@RequestMapping("/persons")
public class PessoaController {
	
	@Value("${api.moviedb.key}")
    private String apiKey;
	
    @Autowired
    private RestTemplate apiRequest;
	
	@Autowired
	private PersonRepository daoPerson;
	
	/* LIST PERSON */
	@GetMapping("/list")
	public String list(Model model) {
		List<Pessoa> listPerson = daoPerson.findAll();
		model.addAttribute("personList", listPerson);
		return "person/listPerson.html";
	}

	/* NEW PERSON */
	@RequestMapping("/new")
	public String newPerson(Model model) {
		Pessoa p = new Pessoa();
		model.addAttribute("person", p);
		return "person/manterPerson.html";
	}
	
	/* SAVE PERSON */
	@PostMapping("/save")
	public String save(@Valid Pessoa pessoa, BindingResult result, Model model) {	
		if (result.hasErrors()) {
			System.out.println("Passando aqui");
			return"person/manterPerson.html";			
		}
		daoPerson.save(pessoa);
		return "redirect:/persons/list";
	}
	
	/* EDIT PERSON */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Pessoa pessoa = daoPerson.findById(id).get();
		model.addAttribute("person", pessoa);
		return "person/manterPerson.html";
	}
	
	/* DELETE PERSON */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		daoPerson.deleteById(id);
		return "redirect:/persons/list";
	}
	
	@GetMapping("/person")
	public String searchByName(@RequestParam String name, Model model) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("id", "id");
		params.put("query", name);
		params.put("popularity", "popularity");
	    params.put("profile_path", "profile_path");
		String personUrl = "https://api.themoviedb.org/3/search/person?api_key={key}&query={query}&popularity={popularity}&profile_path={profile_path}";
		WrapperPersonSearch searchResult = apiRequest.getForObject(personUrl, WrapperPersonSearch.class, params);
 
		Person person = null;
		if (searchResult.getResults()!=null) {
			person = searchResult.getResults().get(0);
			person.setProfile_path("https://image.tmdb.org/t/p/w500/" + person.getProfile_path());
		}
		//assert searchResult != null;
		model.addAttribute(person);	
		return "/person/listPersonTMDB.html";
	}
}
