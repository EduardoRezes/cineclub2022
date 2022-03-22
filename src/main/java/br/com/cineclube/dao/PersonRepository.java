package br.com.cineclube.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cineclube.model.Pessoa;

public interface PersonRepository extends JpaRepository<Pessoa, Long>{

}
