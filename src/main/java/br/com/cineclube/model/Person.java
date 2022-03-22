package br.com.cineclube.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

	private Long id;
	private String name;
	private BigDecimal popularity;
	private String profile_path;
}
