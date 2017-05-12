package com.dsy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小说阅读详情
 * @author qingpeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Document(collection="View")
public class View {
   
	/** id */
    @Id
    private String id;
    /** 小说id */
    @Indexed
    private Integer novel_id;
    /** 年 */
    @Indexed
    private int year;
    /** 月 */
    @Indexed
    private int month;
    /** 日 */
    @Indexed
    private int day;
    /** 阅读次数 */
    private int count;
    
}
