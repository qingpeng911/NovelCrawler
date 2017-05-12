package com.dsy.repository;

import org.springframework.data.repository.CrudRepository;

import com.dsy.model.Category;


public interface CategoryRepository extends CrudRepository<Category, Integer> {

}