package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.vo.AreaVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.DeliveryAreaMapper;
import com.szhengzhu.service.DeliveryAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service("deliveryAreaService")
public class DeliveryAreaServiceImpl implements DeliveryAreaService {

    @Resource
    private DeliveryAreaMapper deliveryAreaMapper;

    @Override
    public DeliveryArea addInfo(DeliveryArea base) {
        List<DeliveryArea> deliveryAreas = deliveryAreaMapper.selectByExampleSelective(base);
        ShowAssert.checkTrue(!deliveryAreas.isEmpty(), StatusCode._4007);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setCreateTime(DateUtil.date());
        base.setServerStatus(false);
        deliveryAreaMapper.insertSelective(base);
        return base;
    }

    @Override
    public DeliveryArea editInfo(DeliveryArea base) {
        deliveryAreaMapper.updateByPrimaryKeySelective(base);
        return base;
    }

    @Override
    public PageGrid<DeliveryArea> getPage(PageParam<DeliveryArea> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(base.getSidx() + " " + base.getSort());
        PageInfo<DeliveryArea> page = new PageInfo<>(
                deliveryAreaMapper.selectByExampleSelective(base.getData()));
        return new PageGrid<>(page);
    }

    @Override
    public DeliveryArea getDeliveryInfo(String markId) {
        return deliveryAreaMapper.selectByPrimaryKey(markId);
    }

    @Override
    public void deleteDelivery(String markId) {
        deliveryAreaMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public void addBatchByProvince(DeliveryArea base, List<AreaVo> list) {
        ShowAssert.checkTrue(list.isEmpty(), StatusCode._5000);
        //去重
        List<String> cityAreaList = deliveryAreaMapper.exitRecords(base.getProvince(), base.getStorehouseId());
        List<DeliveryArea> areasList = new ArrayList<>();
        DeliveryArea info;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        list = list.stream().filter(areaVo -> !cityAreaList.contains(areaVo.getCity() + "," + areaVo.getArea())).collect(Collectors.toList());
        for (AreaVo areaVo : list) {
            info = DeliveryArea.builder().markId(snowflake.nextIdStr()).area(areaVo.getArea()).city(areaVo.getCity()).createTime(DateUtil.date())
                    .creator(base.getCreator()).deliveryPrice(base.getDeliveryPrice()).houseName(base.getHouseName())
                    .limitPrice(base.getLimitPrice()).province(base.getProvince()).serverStatus(false).storehouseId(base.getStorehouseId()).build();
            areasList.add(info);
        }
        if (!areasList.isEmpty()) {
            deliveryAreaMapper.insertBatch(areasList);
        }

    }

    @Override
    public DeliveryArea getDeliveryPrice(String addressId) {
        return deliveryAreaMapper.selectAreaByAddressId(addressId).get(0);
    }

    @Override
    public void updateBatchStatus(DeliveryArea base) {
        deliveryAreaMapper.updateByHouseAndProvince(base.getStorehouseId(), base.getProvince());
    }
}
