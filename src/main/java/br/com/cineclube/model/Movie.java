package br.com.cineclube.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//@JsonIgnore //vai ser filtrado e não enviado no json
	//@NotNull //pega o caso de entrar "            "
	//@NotEmpty //nao aceita valor nulç
	@NotBlank(message = "Campo obrigatório") //não aceita null nem string vazia ""
	@Size(min = 1, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String title;
	

	@NotNull
	private Category category;
	
	@Past // não aceita data futura
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate release;
	
	@Min(message="O campo deve pelo ter 0 ou mais carateres.",value= 0)
	@Max(message="Execedeu o numero maximo do campo.", value= 10)
	@NotNull
	private BigDecimal score;
	
	public Movie() {}
		
	public Movie(String title, Category category, LocalDate release, BigDecimal score) {
		this.title = title;
		this.category = category;
		this.release = release;
		this.score = score;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public LocalDate getRelease() {
		return release;
	}
	public void setRelease(LocalDate release) {
		this.release = release;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
}
