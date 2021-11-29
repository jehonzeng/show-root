package com.szhengzhu.service;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface AttributeService {

    /**
     * 新增系统属性
     * 
     * @date 2019年2月25日 下午3:46:52
     * @param attributeInfo
     * @return
     */
    AttributeInfo saveAttribute(AttributeInfo attributeInfo);
    
    /**
     * 修改系统属性
     * 
     * @date 2019年2月25日 下午3:47:09
     * @param attributeInfo
     * @return
     */
    AttributeInfo updateAttribute(AttributeInfo attributeInfo);
    
    /**
     * 根据主键获取系统属性详情
     * 
     * @date 2019年2月25日 下午3:47:16
     * @param markId
     * @return
     */
    AttributeInfo getAttributeById(String markId);
    
    /**
     * 获取系统属性分页列表
     * @date 2019年2月25日 下午3:47:38
     * @param attrPage
     * @return
     */
    PageGrid<AttributeInfo> pageAttribute(PageParam<AttributeInfo> attrPage);
    
    /**
     * 通过类型值代码获取下拉值
     * 
     * @date 2019年3月15日 下午3:51:20
     * @param type
     * @return
     */
    List<Combobox> listCombobox(String type);

    /**
     *
     * 获取编码
     * @param name
     * @return
     * @date 2019年9月19日
     */
    String getCodeByName(String name);
}
