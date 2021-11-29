package com.szhengzhu.service;

import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface GoodsTypeService {

    /**
     * 添加商品类型
     * 
     * @date 2019年3月12日 下午5:47:01
     * @param base
     * @return
     */
    GoodsType addType(GoodsType base);

    /**
     * 修改商品类型
     * 
     * @date 2019年3月12日 下午5:46:59
     * @param base
     * @return
     */
    GoodsType modifyGoodsType(GoodsType base);

    /**
     * 商品分页信息
     * 
     * @date 2019年3月12日 下午5:46:57
     * @param base
     * @return
     */
    PageGrid<GoodsType> getPage(PageParam<GoodsType> base);
    
    /**
     * 获取类型下拉列表
     * 
     * @date 2019年3月18日 下午4:03:15
     * @return
     */
    List<Combobox> listCombobox();
    
    /**
     * 批量添加类型与规格关联关系
     * 
     * @date 2019年6月19日 下午2:01:22
     * @param specIds
     * @param typeId
     * @return
     */
    void addTypeSpec(String[] specIds, String typeId);
    
    /**
     * 删除类型与规格关联关系
     * 
     * @date 2019年6月19日 下午2:02:08
     * @param typeId
     * @return
     */
    void removeTypeSpec(String typeId, String specId);
    
    /**
     * 修改类型与规格关联关系
     * 
     * @date 2019年6月20日 下午6:26:04
     * @param typeSpec
     * @return
     */
    void modifyTypeSpec(TypeSpec typeSpec);

}
