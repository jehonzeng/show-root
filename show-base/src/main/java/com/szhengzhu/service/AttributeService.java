package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface AttributeService {

    /**
     * 新增系统属性
     * 
     * @date 2019年2月25日 下午3:46:52
     * @param attributeInfo
     * @return
     */
    Result<AttributeInfo> saveAttribute(AttributeInfo attributeInfo);
    
    /**
     * 修改系统属性
     * 
     * @date 2019年2月25日 下午3:47:09
     * @param attributeInfo
     * @return
     */
    Result<AttributeInfo> updateAttribute(AttributeInfo attributeInfo);
    
    /**
     * 根据主键获取系统属性详情
     * 
     * @date 2019年2月25日 下午3:47:16
     * @param markId
     * @return
     */
    Result<AttributeInfo> getAttributeById(String markId);
    
    /**
     * 获取系统属性分页列表
     * @date 2019年2月25日 下午3:47:38
     * @param attrPage
     * @return
     */
    Result<PageGrid<AttributeInfo>> pageAttribute(PageParam<AttributeInfo> attrPage);
    
    /**
     * 通过类型值代码获取下拉值
     * 
     * @date 2019年3月15日 下午3:51:20
     * @param type
     * @return
     */
    Result<List<Combobox>> listCombobox(String type);
}
