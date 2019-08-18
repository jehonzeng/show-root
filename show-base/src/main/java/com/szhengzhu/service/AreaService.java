package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Result;

public interface AreaService {

    /**
     * 获取三级地址列表
     * 
     * @date 2019年3月22日 下午6:37:26
     * @return
     */
    Result<List<AreaInfo>> listArea();

    /**
     * 获取省份列表
     * 
     * @date 2019年6月14日 下午5:47:49
     * @return
     */
    Result<List<Combobox>> listProvince();

    /**
     * 
     * 
     * @date 2019年6月14日 下午6:41:35
     * @param province
     * @return
     */
    Result<List<AreaVo>> listCityAndArea(String province);
}
