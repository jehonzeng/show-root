package com.szhengzhu.service;

import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface DeliveryAreaService {
   
    /**添加配送区域信息
     * @date 2019年3月14日 下午6:36:00
     * @param base
     * @return
     */
    DeliveryArea addInfo(DeliveryArea base);
    
    /**修改配送区域信息
     * @date 2019年3月14日 下午6:35:58
     * @param base
     * @return
     */
    DeliveryArea editInfo(DeliveryArea base);
    
    /**获取配送区域信息分页列表
     * @date 2019年3月14日 下午6:35:55
     * @param base
     * @return
     */
    PageGrid<DeliveryArea> getPage(PageParam<DeliveryArea> base);

    /**
     * 根据id获取配送区域信息
     * 
     * @date 2019年3月25日 下午5:08:23
     * @param markId
     * @return
     */
    DeliveryArea getDeliveryInfo(String markId);
    
    /**
     * 删除配送信息
     * 
     * @date 2019年4月15日 下午5:59:58
     * @param markId
     * @return
     */
    void deleteDelivery(String markId);

    /**
     * 批量添加某个省份下的所有配送区域
     * 
     * @date 2019年6月14日 下午5:31:57
     * @param base
     * @param list 
     * @return
     */
    void addBatchByProvince(DeliveryArea base, List<AreaVo> list);
    
    /**
     * 获取地址对应配送费信息
     * 
     * @date 2019年10月12日 下午2:53:38
     * @param addressId
     * @return
     */
    DeliveryArea getDeliveryPrice(String addressId);
    

    /**
     * 在指定仓库下启用指定省份下的所有配送范围
     * 
     * @param base
     * @return
     */
    void updateBatchStatus(DeliveryArea base);
 
}
