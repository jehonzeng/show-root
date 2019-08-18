package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface MealService {

    /**
     * 添加套餐信息
     * 
     * @date 2019年4月18日 下午4:49:58
     * @param base
     * @return
     */
    Result<?> addMeal(MealInfo base);

    /**
     * 添加套餐单品
     * 
     * @date 2019年4月18日 下午4:50:00
     * @param base
     * @return
     */
    Result<?> addItem(MealItem base);

    /**
     * 获取套餐分页列表
     * 
     * @date 2019年4月18日 下午4:50:03
     * @param base
     * @return
     */
    Result<PageGrid<MealInfo>> getPage(PageParam<MealInfo> base);

    /**
     * 获取套餐单品分页列表
     * 
     * @date 2019年4月18日 下午4:50:05
     * @param base
     * @return
     */
    Result<PageGrid<MealVo>> getItemPage(PageParam<MealItem> base);

    /**
     * 修改套餐信息
     * 
     * @date 2019年4月18日 下午4:50:07
     * @param base
     * @return
     */
    Result<?> editMeal(MealInfo base);
    
    /**
     * 获取套餐下拉列表
     * 
     * @date 2019年5月20日 下午3:08:29
     * @return
     */
    Result<List<Combobox>> listCombobox();

    /**
     * 修改套餐单品信息
     * 
     * @date 2019年4月18日 下午4:50:09
     * @param base
     * @return
     */
    Result<?> editMealItem(MealItem base);

    /**
     * 根据id获取套餐信息
     * 
     * @date 2019年4月18日 下午4:50:12
     * @param markId
     * @return
     */
    Result<?> getMealById(String markId);

    /**
     * 根据id获取套餐单品信息
     * 
     * @date 2019年4月23日 上午10:43:27
     * @param markId
     * @return
     */
    Result<?> getMealItemById(String markId);

    /**
     * 下拉列表
     * 
     * @date 2019年5月21日 下午6:11:59
     * @return
     */
    Result<List<Combobox>> getMealList();

    /**
     * 不在栏目中的套餐分页列表
     * 
     * @date 2019年5月28日 下午6:13:07
     * @param base
     * @return
     */
    Result<PageGrid<MealInfo>> getPageByColumn(PageParam<MealInfo> base);

    /**
     * 不在标签分类中的套餐分页列表
     * 
     * @date 2019年5月29日 上午11:32:59
     * @param base
     * @return
     */
    Result<PageGrid<MealInfo>> getPageByLabel(PageParam<MealInfo> base);

    /**
     * 不在特价中的套餐分页列表
     * 
     * @date 2019年5月29日 下午7:27:59
     * @param base
     * @return
     */
    Result<PageGrid<MealInfo>> getPageBySpecial(PageParam<MealInfo> base);

    /**
     * 不在图标中的套餐分页列表
     * 
     * @date 2019年5月29日 下午7:28:02
     * @param base
     * @return
     */
    Result<PageGrid<MealInfo>> getPageByIcon(PageParam<MealInfo> base);
    
    /**
     * 前端获取套餐详细信息
     * 
     * @date 2019年6月20日 上午11:36:45
     * @param mealId
     * @return
     */
    Result<GoodsInfoVo> getMealDetail(String mealId, String userId);

    /**
     * 获取已存在的服务列表
     * 
     * @date 2019年6月20日 下午5:01:57
     * @param mealId
     * @return
     */
    Result<List<String>> getServerListInMeal(String mealId);

    /**
     * 批量添加服务
     * 
     * @date 2019年6月20日 下午5:02:00
     * @param base
     * @return
     */
    Result<?> addBatchMealServe(BatchVo base);

    /**
     * 批量刪除服务
     * 
     * @date 2019年6月20日 下午5:02:02
     * @param base
     * @return
     */
    Result<?> deleteBatchMealServe(BatchVo base);
    
    /**
     * 获取套餐库存信息
     * 
     * @date 2019年7月8日 下午4:53:51
     * @param mealId
     * @param addressId
     * @return
     */
    Result<StockBase> getStockInfo(String mealId, String addressId);
}
