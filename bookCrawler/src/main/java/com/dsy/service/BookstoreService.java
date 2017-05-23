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
	
	private ExecutorService threadPool = new ThreadPoolExecutor(0, 2, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
	
	
	/**
	 * 启动服务器时自动调用该方法，开始扒取
	 */
	@PostConstruct
	public void startCrawler() {
		//1.扒取小说基本信息
		// crawlsBook();
		//2.扒取小说目录
		 crawlsCatalog();
	}
	
	
	/**
	 * 1.扒取小说基本信息
	 */
	public void crawlsBook(){
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
//					threadPool.submit(new Runnable() {
//						public void run() {
							doSave(el);							
//						}
//					});
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
			
			//保存作者
			Author author = new Author(authorObj.get("name").getAsString());
			if (authorRepository.findByName(author.getName()) == null) {
				authorRepository.save(author);
			}
			
			//保存小说基本信息
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
			
			//保存小说阅读次数记录
			View view = new View();
			view.setCount(dataObj.get("dayvisit").getAsInt());
			view.setNovel_id(novelObj.get("id").getAsInt());
			view.setDay(DateUtil.getDay(new Date()));
			view.setMonth(DateUtil.getMonth(new Date()));
			view.setYear(DateUtil.getYear(new Date()));
			viewRepository.save(view);
			
			//保存小说分类
			Category category = new Category(categoryObj.get("id").getAsInt(), categoryObj.get("name").getAsString());
			categoryRepository.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//重置小说目录抓取标识
//	public void restNovel(){
//		List<Novel> novels = novelRepository.findByCompeletCatalog(1);
//		log.info("已经标记数："+novels.size());
//		for (Novel novel : novels) {
//			novel.setCompeletCatalog(null);
//			novelRepository.save(novel);
//		}
//		novels = novelRepository.findByCompeletCatalog(1);
//		log.info("是否重置完成："+(novels.size() == 0));
//	}
	
	/**
	 * 2.根据已有的小说信息，扒取对应小说目录
	 */
	public void crawlsCatalog() {
		//获取所有未扒取目录的小说id集合 大概10W本左右（可以保证一次未扒完 下次继续）
		List<Novel> novels = novelRepository.findUnCompeletNovelIds();
		log.info("待抓取目录的小说总数："+novels.size());
		//一次扒一万本
		for (int i = 0; i < novels.size(); i++) {
			try {
				Novel novel = novels.get(i);
				//保存目录
				saveCatalog(novel);
			} catch (Exception e) {
				log.error("Error:小说id:"+novels.get(i).getId(), e);
			}
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
			//能抓取到目录的，标记为已完成
			Novel n = novelRepository.findOne(novel.getId());
			if (catalogs.size() > 0) {
				//添加完成标识
				n.setCompeletCatalog(1);
				novelRepository.save(n);				
				log.info("【完成】-小说id："+n.getId()+" 总章节："+catalogs.size());
			}else {
				novelRepository.delete(novel_id);
				log.info("【删除】-小说id："+n.getId()+" 总章节："+catalogs.size());
			}
		}
	}
}