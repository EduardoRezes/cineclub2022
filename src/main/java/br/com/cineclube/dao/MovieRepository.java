package br.com.cineclube.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cineclube.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
	//Extendendo JpaREpository os metodos CRUD(findAll, FindByID, get, save, delete por padrão
}
