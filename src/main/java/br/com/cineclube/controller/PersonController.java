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

import br.com.cineclube.dao.PersonRepository;
import br.com.cineclube.model.Person;

@Controller
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonRepository daoPerson;
	
	/* LIST PERSON */
	@GetMapping("/list")
	public String list(Model model) {
		List<Person> listPerson = daoPerson.findAll();
		model.addAttribute("personList", listPerson);
		return "person/listPerson.html";
	}

	/* NEW PERSON */
	@PostMapping("/new")
	public String newPerson(Model model) {
		Person p = new Person();
		model.addAttribute("person", p);
		return "person/manterPerson.html";
	}
	
	/* SAVE PERSON */
	@PostMapping("/save")
	public String save(@Valid Person person, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return"person/manterPerson";
		}
		daoPerson.save(person);
		return "redirect:/persons/list";
	}
	
	/* EDIT PERSON */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Person person = daoPerson.findById(id).get();
		model.addAttribute("person", person);
		return "person/manterPerson.html";
	}
	
	/* DELETE PERSON */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		daoPerson.deleteById(id);
		return "redirect:/persons/list";
	}
}
