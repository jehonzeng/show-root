package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.vo.DishesImageVo;
import com.szhengzhu.bean.member.vo.DishesStageVo;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.DishesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("dishesService")
public class DishesServiceImpl implements DishesService {

    @Resource
    private Sender sender;

    @Resource
    private DishesInfoMapper dishesInfoMapper;

    @Resource
    private DishesStageMapper dishesStageMapper;

    @Resource
    private DishesImageMapper dishesImageMapper;

    @Resource
    private ReceiveDishesMapper receiveDishesMapper;

    @Override
    public PageGrid<DishesInfo> queryDishes(PageParam<DishesInfo> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        PageInfo<DishesInfo> pageInfo = new PageInfo<>(dishesInfoMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addDishes(DishesInfo dishesInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        dishesInfo.setMarkId(snowflake.nextIdStr());
        dishesInfo.setStatus(1);
        dishesInfo.setDays(5);
        dishesInfo.setCreateTime(DateUtil.date());
        dishesInfoMapper.insert(dishesInfo);
    }

    @Override
    public void modifyDishes(DishesInfo dishesInfo) {
        dishesInfo.setModifyTime(DateUtil.date());
        dishesInfoMapper.update(dishesInfo);
    }

    @Override
    public List<DishesStage> queryStage(DishesStageVo stage) {
        return dishesStageMapper.queryAll(stage);
    }

    @Override
    public void addStage(DishesStageVo stage) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        stage.setMarkId(markId);
        stage.setCreateTime(DateUtil.date());
        dishesStageMapper.insert(stage);
    }

    @Override
    public void modifyStage(DishesStageVo stage) {
        stage.setModifyTime(DateUtil.date());
        dishesStageMapper.update(stage);
    }

    @Override
    public List<DishesImage> queryImage(DishesImageVo image) {
        return dishesImageMapper.queryAll(image);
    }

    @Override
    public void addImage(DishesImageVo image) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        image.setMarkId(markId);
        image.setCreateTime(DateUtil.date());
        dishesImageMapper.insert(image);
        ReceiveVo receiveVo = receiveDishesMapper.queryById(image.getReceiveId());
        sender.dishesStage(receiveVo);
    }

    @Override
    public void modifyImage(DishesImageVo image) {
        image.setModifyTime(DateUtil.date());
        dishesImageMapper.update(image);
    }
}
