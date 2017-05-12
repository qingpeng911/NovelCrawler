package com.dsy.repository;

import org.springframework.data.repository.CrudRepository;

import com.dsy.model.Author;


public interface AuthorRepository extends CrudRepository<Author, String> {

	Author findByName(String name);

}