package br.com.cineclube.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cineclube.dao.PersonRepository;
import br.com.cineclube.model.Person;

@Controller
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonRepository daoPerson;
	

	/* NEW PERSON */
	@PostMapping("/new")
	public String newPerson(Model model) {
		Person p = new Person();
		model.addAttribute(p);
		return "person/manterPerson";
	}
	
	/* SAVE PERSON */
	
	/* EDIT PERSON */

	/* DELETE PERSON */
	
}
