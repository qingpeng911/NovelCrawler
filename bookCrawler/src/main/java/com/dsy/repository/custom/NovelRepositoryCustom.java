package com.dsy.repository.custom;

import java.util.List;

import com.dsy.model.Novel;

public interface NovelRepositoryCustom {
	List<Novel> findUnCompeletNovelIds();
}
