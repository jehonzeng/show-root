package com.szhengzhu.service;

import com.szhengzhu.bean.base.CounselInfo;
import com.szhengzhu.bean.base.ProductContent;
import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.vo.NewsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface WebsiteService {

     /**
     * 获取产品分页信息
     *
     * @param base
     * @return
     * @date 2019年8月23日
     */
    PageGrid<ProductInfo> getProductPage(PageParam<ProductInfo> base);

     /**
     *
     * 添加信息
     *
     * @param base
     * @return
     * @date 2019年8月23日
     */
    ProductInfo addProductInfo(ProductInfo base);


     /**
     * 编辑信息
     *
     * @param base
     * @return
     * @date 2019年8月23日
     */
    ProductInfo editProductInfo(ProductInfo base);

    /**
     * 获取编辑信息
     *
     * @param markId
     * @return
     * @date 2019年8月23日
     */
    ProductInfo getProductInfo(String markId);


     /**
     *  获取产品内容
     *
     * @param productId
     * @return
     * @date 2019年8月23日
     */
    ProductContent getContent(String productId);

    /**
     *  编辑内容
     * @param base
     * @return
     */
    ProductContent editContent(ProductContent base);

    /**
     * 获取用户咨询分页信息
     *
     * @param base
     * @return
     * @date 2019年8月23日
     */
    PageGrid<CounselInfo> getCounselPage(PageParam<CounselInfo> base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年8月23日
     */
    void addConselInfo(CounselInfo base);


    /**
     * 前端获取商品列表信息
     *
     * @return
     * @date 2019年8月23日
     */
    List<ProductInfo> getGoodsList();

    /**
     * 前端获取新闻分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @date 2019年8月23日
     */
    PageGrid<ProductInfo> getNewList(Integer pageNo,Integer pageSize);

    /**
     * 获取新闻详情信息
     *
     * @param markId
     * @return
     * @date 2019年8月23日
     */
    NewsVo getNewsDetail(String markId);

}
