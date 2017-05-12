package com.dsy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 作者
 * 
 * @author qingpeng
 */
@Data
@Document(collection = "Author")
public class Author {

	/** id */
	@Id
	private String id;
	/** 作者名 */
	private String name;

	public Author(String name) {
		this.name = name;
	}

	public Author() {
	}

}
