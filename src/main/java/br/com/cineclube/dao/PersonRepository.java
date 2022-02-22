package br.com.cineclube.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cineclube.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
