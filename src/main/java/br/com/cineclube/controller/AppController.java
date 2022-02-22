package br.com.cineclube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {
	/*Controler destinada para rotas index,filmes, atores, etc...*/
	
	@RequestMapping("/")
	public String index() {
		return "index.html";
	}
}
