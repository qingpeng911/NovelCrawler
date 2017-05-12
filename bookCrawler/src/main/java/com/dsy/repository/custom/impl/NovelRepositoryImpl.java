package com.dsy.repository.custom.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.dsy.model.Novel;
import com.dsy.repository.custom.NovelRepositoryCustom;

public class NovelRepositoryImpl implements NovelRepositoryCustom {

	private static final Log log = LogFactory.getLog(NovelRepositoryImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public List<Novel> findUnCompeletNovelIds() {
		Criteria criteria = Criteria.where("compeletCatalog").ne(1);
		Query query = new Query(criteria);
		query.fields().include("_id");
		List<Novel> novels = mongoTemplate.find(query, Novel.class);	
		return novels;
	}

}
