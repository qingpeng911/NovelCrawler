package com.dsy.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小说目录
 * @author qingpeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document(collection = "Catalog")
public class Catalog {

	@Id
	/** 章节id */
	private Integer id;
	/** 小说id */
	@Indexed
	private Integer novel_id;
	/** 章节编号 */
	private Integer oid;
	/** 章节名 */
	private String name;
	/** 上传时间 */
	private Date createTime;
	/** 文本地址 */
	private String url;
}
