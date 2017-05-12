package com.dsy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dsy.model.Novel;
import com.dsy.repository.custom.NovelRepositoryCustom;


public interface NovelRepository extends NovelRepositoryCustom,CrudRepository<Novel, Integer> {




}