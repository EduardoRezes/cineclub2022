package br.com.cineclube.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
	
	private Long id;
	private String title;
	private String overview;
	private BigDecimal vote_average;
	private String poster_path;
	
}