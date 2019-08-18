package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Result;
import com.szhengzhu.mapper.AreaInfoMapper;
import com.szhengzhu.service.AreaService;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaInfoMapper areaInfoMapper;
    
    @Override
    public Result<List<AreaInfo>> listArea() {
        List<AreaInfo> areas= areaInfoMapper.selectAll();
        return new Result<>(areas);
    }
    
    @Override
    public Result<List<Combobox>> listProvince() {
        List<Combobox> list = areaInfoMapper.selectProvinceList();
        return new Result<>(list);
    }

    @Override
    public Result<List<AreaVo>> listCityAndArea(String province) {
        List<AreaVo> list = areaInfoMapper.selectCityAndAreaList(province);
        return new Result<>(list);
    }
}
