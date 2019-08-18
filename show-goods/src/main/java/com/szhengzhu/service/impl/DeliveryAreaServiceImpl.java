package com.szhengzhu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.DeliveryAreaMapper;
import com.szhengzhu.service.DeliveryAreaService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

@Service("deliveryAreaService")
public class DeliveryAreaServiceImpl implements DeliveryAreaService {

    @Autowired
    private DeliveryAreaMapper deliveryAreaMapper;

    @Override
    public Result<?> addInfo(DeliveryArea base) {
        if (base == null || StringUtils.isEmpty(base.getStorehouseId()) || StringUtils.isEmpty(base.getProvince())
                || StringUtils.isEmpty(base.getCity()) || StringUtils.isEmpty(base.getArea())
                || base.getLimitPrice() == null || base.getDeliveryPrice() == null)
            return new Result<>(StatusCode._4004);
        List<DeliveryArea> deliveryAreas = deliveryAreaMapper.selectByExampleSelective(base);
        if (deliveryAreas.size() > 0)
            return new Result<>(StatusCode._4007);
        base.setMarkId(IdGenerator.getInstance().nexId());
        base.setCreateTime(TimeUtils.today());
        base.setServerStatus(false);
        deliveryAreaMapper.insertSelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<?> editInfo(DeliveryArea base) {
        if (base == null || base.getMarkId() == null)
            return new Result<>(StatusCode._4004);
        deliveryAreaMapper.updateByPrimaryKeySelective(base);
        return new Result<>(base);
    }

    @Override
    public Result<PageGrid<DeliveryArea>> getPage(PageParam<DeliveryArea> base) {
        PageHelper.startPage(base.getPageIndex(), base.getPageSize());
        PageInfo<DeliveryArea> page = new PageInfo<DeliveryArea>(
                deliveryAreaMapper.selectByExampleSelective(base.getData()));
        return new Result<>(new PageGrid<>(page));
    }

    @Override
    public Result<?> getDeliveryInfo(String markId) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4008);
        DeliveryArea info = deliveryAreaMapper.selectByPrimaryKey(markId);
        return new Result<>(info);
    }

    @Override
    public Result<?> deleteDelivery(String markId) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4008);
        deliveryAreaMapper.deleteByPrimaryKey(markId);
        return new Result<>();
    }

    @Override
    public Result<?> addBatchByProvince(DeliveryArea base, List<AreaVo> list) {
        DeliveryArea info = null ;
        AreaVo areaVo = null;
        List<DeliveryArea> areasList = new ArrayList<>();
        IdGenerator idGenerator = IdGenerator.getInstance();
        if(list != null && list.size() > 0) {
            for(int i = 0,len = list.size(); i< len;i++) {
                areaVo = list.get(i);
                info = new DeliveryArea();
                info.setMarkId(idGenerator.nexId());
                info.setArea(areaVo.getArea());
                info.setCity(areaVo.getCity());
                info.setCreateTime(TimeUtils.today());
                info.setCreator(base.getCreator());
                info.setDeliveryPrice(base.getDeliveryPrice());
                info.setHouseName(base.getHouseName());
                info.setLimitPrice(base.getLimitPrice());
                info.setProvince(base.getProvince());
                info.setServerStatus(false);
                info.setStorehouseId(base.getStorehouseId());
                areasList.add(info);
            }
            if(areasList.size() > 0)
                deliveryAreaMapper.insertBatch(areasList);
            return new Result<>();
        }
        return new Result<>(StatusCode._5000);
    }
}
