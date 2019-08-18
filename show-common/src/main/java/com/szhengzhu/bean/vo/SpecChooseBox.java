package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 选项框列表显示
 * 
 * @author Administrator
 * @date 2019年3月22日
 */
@Data
public class SpecChooseBox implements Serializable {

    private static final long serialVersionUID = 6001220178671905696L;

    private String attrName;

    private List<Combobox> list;

}
