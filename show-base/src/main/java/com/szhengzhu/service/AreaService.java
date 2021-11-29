package com.szhengzhu.service;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;

import java.util.List;
import java.util.Map;

public interface AreaService {
    
    /**
     * 获取三级地址列表
     * 
     * @date 2019年3月22日 下午6:37:26
     * @return
     */
    List<AreaInfo> listArea();

    /**
     * 对比版本返回数据
     * 
     * @date 2019年3月22日 下午6:37:26
     * @return
     */
    Map<String, Object> listArea(int version);

    /**
     * 获取省份列表
     * 
     * @date 2019年6月14日 下午5:47:49
     * @return
     */
    List<Combobox> listProvince();

    /**
     * 
     * 
     * @date 2019年6月14日 下午6:41:35
     * @param province
     * @return
     */
    List<AreaVo> listCityAndArea(String province);
}
