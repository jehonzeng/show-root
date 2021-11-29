package com.szhengzhu.bean.excel;

import java.io.Serializable;

import com.szhengzhu.annotation.Excel;

public class SeatsVo implements Serializable{

	private static final long serialVersionUID = 4108480318524334723L;
	
	@Excel(name = "桌台类型/入座时间",sort = 1)
	private String times;
	
	@Excel(name = "2人桌/（桌）",sort = 2)
	private Integer two;
	
	@Excel(name = "4人桌/（桌）",sort = 3)
	private Integer four;
	
	@Excel(name = "6人桌/（桌）",sort = 4)
	private Integer six;
	
	@Excel(name = "8人桌/（桌）",sort = 5)
	private Integer eight;
	
	@Excel(name = "10人桌/（桌）",sort = 6)
	private Integer ten;
	
}
