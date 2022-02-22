package br.com.cineclube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinecluneApplication {
/* Roteiro
 * Desacoplar a view do backend
 * backend == controller
 * frontend == thtmeleaf
 * 
 * Backend - gerar uma saida em json (Serializar)
 * utilizar outros frontends(android, react, js, ...) para consumir os serviçoes (Endpoint)
 * Definir API (application program interface) Get /filmes/3 jason filme
 * */
	
	public static void main(String[] args) {
		SpringApplication.run(CinecluneApplication.class, args);
	}

}
