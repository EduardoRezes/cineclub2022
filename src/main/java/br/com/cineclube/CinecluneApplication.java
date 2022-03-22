package br.com.cineclube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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

	@Bean 
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
