# Consumindo serviço API

Objetivo: Integrar a API do site TMDB com o projeto cinemaclube.

API: https://www.themoviedb.org/

## TMDB

API docs: 
- https://www.themoviedb.org/documentation/api
- https://developers.themoviedb.org/3/getting-started/introduction

Para utilizar a API é necessário criar uma chave para usar nas requisições.
- Criar uma conta
- Acessar para gerar a chave: https://www.themoviedb.org/settings/api

## Exemplos

### Search movie

```bash
http -v https://api.themoviedb.org/3/search/movie?api_key=API_KEY_HERE language==en-US query==avatar page==1 include_adult==false year==2009
```
## Spring Boot

## `application.properties`
Adicionar api key no arquivo `application.properties`

`api.moviedb.key=API_KEY_HERE`


## Definir getRestTemplate
No arquivo CineclubeApplication.java

```java
@Bean
public RestTemplate getRestTemplate() {
	return new RestTemplate();
}
```

## Criar classe Movie

Criar classe Movie para armazenar dados da resposta JSON

```java
public class Movie {
	
	private Long id;
	private String title;
	private String overview;
	private BigDecimal vote_average;
	
	// gerar getters e setters ...
}
```


### Criar classe **MovieConsumer.java**

```java
@RestController
@RequestMapping("/api/v1")
public class MovieConsumer {
	
	@Value("${api.moviedb.key}")
    private String apiKey;

    @Autowired
    private RestTemplate apiRequest;
    
    @RequestMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable Long id) {
    	String endpoint = 
        		"https://api.themoviedb.org/3/movie/" + id + "?api_key=" +  apiKey;
        Movie movie = apiRequest.getForObject(endpoint, Movie.class);
        return movie;
    }
}
```

**Testar:**

```bash

http :8080/api/v1/movie/550

```

## Consulta

Quando fazemos uma busca por id, a resposta de retorno representa um **objeto**:

```
http -v https://api.themoviedb.org/3/movie/550?api_key=KEY language==en-US page==1

```

Entretanto, quando fazemos uma busca por search, o retorno é uma **lista**:

```
http -v https://api.themoviedb.org/3/search/movie?api_key=KEY language==en-US query==avatar page==1 include_adult==false year==2009
```

Logo, é necessário tratar esse cenário encapsulando o retorno numa classe Wrapper que representa esse resultado. Por exemplo:

```java
public class WrapperMovieSearch {
	
    private List<Movie> results;

    public List<Movie> getResults() {

        // ordem decrescente == ordena por filmes mais populares
		results.sort( (f1,f2) -> Integer.compare(f2.getVote_count(), f1.getVote_count()) );

		return results;
    }
    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
```

E para chamar a themoviedb API implementamos um método search na MovieConsumer:

```java
@GetMapping("/search")
	public WrapperFilmeSearch searchMovie(@RequestParam String title, @RequestParam String year) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query", title);
		params.put("year", year);
		params.put("lang", "en-US");
		String url = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}&year={year}&language={lang}";
		WrapperFilmeSearch res = apiRequest.getForObject(url, WrapperFilmeSearch.class, params);
		return res;
	}
```

E para retornar apenas 1 filme?

```java
@GetMapping("/movie")
	public Optional<Movie> searchByName(@RequestParam String title, @RequestParam String year) {
		Map<String, String> params = new HashMap<>();
		params.put("key", apiKey);
		params.put("query", title);
		params.put("year", year);
		params.put("lang", "en-US");
		String url = "https://api.themoviedb.org/3/search/movie?api_key={key}&query={query}&year={year}&language={lang}";
		WrapperFilmeSearch res = apiRequest.getForObject(url, WrapperFilmeSearch.class, params);
		
		Movie movie = null;
		if (res.getResults()!=null && res.getResults().size()>0)
			movie = res.getResults().get(0);
		return Optional.ofNullable(movie);
	}
```

Obter a imagem do filme:

O resultado retornado possui um atributo `poster_path` que contém a URI para obter a imagem da capa do filme.

```
"poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg"

```

Para obter a URL completa basta concatenar esse atributo para formar a URL:
```
https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg

```

