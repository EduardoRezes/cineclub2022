package br.com.cineclube.model;

import java.util.List;

public class WrapperMovieSearch {
	
	 private List<Movie> results;

	    public List<Movie> getResults() {

	        // ordem decrescente == ordena por filmes mais populares
			results.sort( (f1,f2) -> f2.getVote_average().compareTo(f1.getVote_average()));

			return results;
	    }
	    public void setResults(List<Movie> results) {
	        this.results = results;
	    }    
}
