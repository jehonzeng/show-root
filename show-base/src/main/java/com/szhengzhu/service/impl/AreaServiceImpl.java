package com.szhengzhu.service.impl;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.mapper.AreaInfoMapper;
import com.szhengzhu.mapper.AttributeInfoMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 */
@Service("areaService")
public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaInfoMapper areaInfoMapper;

    @Resource
    private AttributeInfoMapper attributeInfoMapper;

    @Resource
    private Redis redis;

    @Override
    public List<AreaInfo> listArea() {
        return areaInfoMapper.selectAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> listArea(int version) {
        Map<String, Object> result = new HashMap<>(4);
        String ver = attributeInfoMapper.selectVersion();
        result.put("version", Integer.valueOf(ver));
        List<AreaInfo> areaList;
        if (String.valueOf(version).equals(ver)) {
            result.put("areaList", new ArrayList<>());
            return result;
        }
        areaList = (List<AreaInfo>) redis.get("base:area:list");
        if (areaList != null) {
            result.put("areaList", areaList);
            return result;
        }
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            areaList = (List<AreaInfo>) redis.get("base:area:list");
            if (areaList == null) {
                areaList = areaInfoMapper.selectAll();
                redis.set("base:area:list", areaList, 7 * 24 * 60 * 60L);
            }
        } finally {
            lock.unlock();
        }
        result.put("areaList", areaList);
        return result;
    }

    @Override
    public List<Combobox> listProvince() {
        return areaInfoMapper.selectProvinceList();
    }

    @Override
    public List<AreaVo> listCityAndArea(String province) {
        return areaInfoMapper.selectCityAndAreaList(province);
    }
}
