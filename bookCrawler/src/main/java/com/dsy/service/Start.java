package com.dsy.service;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Start {
	
	private static final Log log = LogFactory.getLog(Start.class);
	
	@Autowired
	private BookstoreService bookstoreService;
	
	/**
	 * 启动服务器时自动调用该方法，开始扒取
	 */
	@PostConstruct
	private void startCrawler() {
		//1.扒取小说基本信息
		bookstoreService.crawlsBook();
		//2.扒取小说目录
		bookstoreService.crawlsCatalog();
	}
}