package br.com.cineclube.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "campo obrigatório")
	@Size(min = 3, max = 50, message = "Campo deve conter entre {min} e {max} carácteres")
	private String name;
	
	@Past
	@NotNull(message = "Campo obrigatório")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	
	private Integer age;
	
	public Pessoa() {}

	public Pessoa(String name, LocalDate birthday) {
		this.name = name;
		this.birthday = birthday;
	}

}
