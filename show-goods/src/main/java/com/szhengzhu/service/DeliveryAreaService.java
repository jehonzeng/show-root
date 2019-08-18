package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface DeliveryAreaService {
   
    /**添加配送区域信息
     * @date 2019年3月14日 下午6:36:00
     * @param base
     * @return
     */
    Result<?> addInfo(DeliveryArea base);
    
    /**修改配送区域信息
     * @date 2019年3月14日 下午6:35:58
     * @param base
     * @return
     */
    Result<?> editInfo(DeliveryArea base);
    
    /**获取配送区域信息分页列表
     * @date 2019年3月14日 下午6:35:55
     * @param base
     * @return
     */
    Result<PageGrid<DeliveryArea>> getPage(PageParam<DeliveryArea> base);

    /**
     * 根据id获取配送区域信息
     * 
     * @date 2019年3月25日 下午5:08:23
     * @param markId
     * @return
     */
    Result<?> getDeliveryInfo(String markId);
    
    /**
     * 删除配送信息
     * 
     * @date 2019年4月15日 下午5:59:58
     * @param markId
     * @return
     */
    Result<?> deleteDelivery(String markId);

    /**
     * 批量添加某个省份下的所有配送区域
     * 
     * @date 2019年6月14日 下午5:31:57
     * @param base
     * @param list 
     * @return
     */
    Result<?> addBatchByProvince(DeliveryArea base, List<AreaVo> list);

}
