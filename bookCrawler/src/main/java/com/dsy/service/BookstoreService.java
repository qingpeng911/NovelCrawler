package com.dsy.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.dsy.model.Author;
import com.dsy.model.Catalog;
import com.dsy.model.Category;
import com.dsy.model.Novel;
import com.dsy.model.View;
import com.dsy.repository.AuthorRepository;
import com.dsy.repository.CatalogRepository;
import com.dsy.repository.CategoryRepository;
import com.dsy.repository.NovelRepository;
import com.dsy.repository.ViewRepository;
import com.dsy.util.CrawlerUtil;
import com.dsy.util.DateUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookstoreService {
	
	private static final Log log = LogFactory.getLog(BookstoreService.class);
	
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private NovelRepository novelRepository;
	@Autowired
	private ViewRepository viewRepository;
	@Autowired
	private CatalogRepository catalogRepository;
	
	private ExecutorService threadPool = new ThreadPoolExecutor(0, 3, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
	
	/**
	 * 扒取小说基本信息
	 */
	private void crawlsBook(){
		boolean hasNext = true;
		int pageNo = 1;
		int pagesize = 500;
		while (hasNext) {
			JsonObject data = CrawlerUtil.get("http://anzhuoapi.yphsy.com:777/api/novel/list.html?pagesize="+pagesize+"&page="+pageNo);
			if (data != null) {
				JsonArray novels = data.getAsJsonArray("data");
				if (novels.size() == 0) {
					hasNext = false;
					break;
				}
				for (JsonElement el : novels) {
					 doSave(el);
				}
			}
			pageNo++;
		}
	}

	private void doSave(JsonElement el) {
		try {
			JsonObject novelObj = el.getAsJsonObject().getAsJsonObject("novel");
			JsonObject authorObj = el.getAsJsonObject().getAsJsonObject("author");
			JsonObject categoryObj = el.getAsJsonObject().getAsJsonObject("category");
			JsonObject lastObj = el.getAsJsonObject().getAsJsonObject("last");
			JsonObject dataObj = el.getAsJsonObject().getAsJsonObject("data");
			
			Author author = new Author(authorObj.get("name").getAsString());
			saveAuthor(author);
			
			Novel novel = new Novel(
					novelObj.get("id").getAsInt(),
					novelObj.get("name").getAsString(), 
					novelObj.get("pinyin").getAsString(),
					novelObj.get("intro").getAsString(),
					novelObj.get("cover").getAsString(),
					authorObj.get("name").getAsString(),
					new Date(novelObj.get("postdate").getAsLong() * 1000),
					novelObj.get("isover").getAsInt() == 1,
					dataObj.get("marknum").getAsInt(), 
					dataObj.get("votenum").getAsInt(),
					dataObj.get("allvisit").getAsLong(),
					lastObj.get("id").getAsInt(), 
					lastObj.get("name").getAsString(), 
					new Date(lastObj.get("time").getAsLong() * 1000),
					null
					);
			novelRepository.save(novel);
			
			View view = new View();
			view.setCount(dataObj.get("dayvisit").getAsInt());
			view.setNovel_id(novelObj.get("id").getAsInt());
			view.setDay(DateUtil.getDay(new Date()));
			view.setMonth(DateUtil.getMonth(new Date()));
			view.setYear(DateUtil.getYear(new Date()));
			viewRepository.save(view);
			
			Category category = new Category(categoryObj.get("id").getAsInt(), categoryObj.get("name").getAsString());
			categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveAuthor(Author author) {
		if (authorRepository.findByName(author.getName()) == null) {
			authorRepository.save(author);
		}
	}
	
	
	/**
	 * 扒取小说目录
	 */
	@PostConstruct
	private void crawlsCatalog() {
		int num = 0;
		List<Novel> novels = novelRepository.findUnCompeletNovelIds();
		log.info("小说总数："+novels.size());
		try {
			//先保存5000本
			for (int i = 0; i < 5000; i++) {
				Novel novel = novels.get(i);
				//保存目录
				saveCatalog(novel);
				
				//添加完成标识
				Novel n = novelRepository.findOne(novel.getId());
				n.setCompeletCatalog(1);
				novelRepository.save(n);
				
				num++;
				if (num % 100 == 0) {
					log.info("累计完成小说："+num);							
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveCatalog(Novel novel) {
		int novel_id = novel.getId();
		JsonObject data = CrawlerUtil.get("http://anzhuoapi.yphsy.com:777/api/novel/dir.html?fromid=1&novelid="+novel_id);
		if (data != null) {
			JsonArray catalogs = data.getAsJsonArray("data");
			for (JsonElement el : catalogs) {
				 JsonObject o = el.getAsJsonObject();
				 Catalog c = new Catalog();
				 c.setId(o.get("id").getAsInt());
				 c.setCreateTime(new Date(o.get("time").getAsLong() * 1000));
				 c.setName(o.get("name").getAsString());
				 c.setOid(o.get("oid").getAsInt());
				 c.setUrl(o.get("url").getAsString());
				 c.setNovel_id(novel_id);
				 catalogRepository.save(c);
			}
		}
	}
}