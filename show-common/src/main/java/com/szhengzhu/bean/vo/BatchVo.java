package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

/**
 * 自定义批量接收对象
 * 
 * @author Administrator
 * @date 2019年4月2日
 */
@Data
public class BatchVo implements Serializable{

    private static final long serialVersionUID = 2719106398934873647L;
    
    private String commonId;

    private Set<String> ids;
    
    private Integer type;//0：普通商品2：表示套餐


}
