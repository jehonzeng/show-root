package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 新闻详情模板信息
 * @author Administrator
 *
 */
@Data
public class NewsVo implements Serializable{
    
    private static final long serialVersionUID = -8154442545014437356L;

    private String title;
	
	private Date time;
	
	private String content;
}
