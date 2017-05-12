package com.dsy.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小说
 * 
 * @author qingpeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document(collection = "Novel")
public class Novel {

	/** 小说id */
	@Id
	private Integer id;
	/** 小说名 */
	private String name;
	/** 拼音 */
	private String pinyin;
	/** 简介 */
	private String intro;
	/** 封面 */
	private String cover;
	/** 作者 */
	private String author;
	/** 上传时间 */
	private Date createTime;
	/** 是否完结 */
	private boolean over;

	/** 收藏数 */
	private int mark;
	/** 推荐数 */
	private int vote;
	/** 总阅读数 */
	private long allView;

	/** 最新章节id */
	private Integer chapterId;
	/** 最新章节名 */
	private String lastName;
	/** 最新更新时间 */
	private Date lastUpdate;

	/** 小说目录是否已经扒完  1:完成*/
	private Integer compeletCatalog;
}
