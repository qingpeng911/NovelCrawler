package com.dsy.repository;

import org.springframework.data.repository.CrudRepository;

import com.dsy.model.Catalog;
import com.dsy.repository.custom.NovelRepositoryCustom;


public interface CatalogRepository extends CrudRepository<Catalog, Integer> {

}