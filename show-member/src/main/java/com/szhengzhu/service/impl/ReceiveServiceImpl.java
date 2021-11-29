package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.*;
import com.szhengzhu.bean.member.vo.ReceiveVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.ReceiveInfo;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.ReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Administrator
 */
@Slf4j
@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private PendDishesMapper pendDishesMapper;

    @Resource
    private ReceiveRecordMapper receiveRecordMapper;

    @Resource
    private ReceiveDishesMapper receiveDishesMapper;

    @Resource
    private DishesStageMapper dishesStageMapper;

    @Override
    public void addReceive(ReceiveDishes receiveDishes) {
        ShowAssert.checkTrue(ObjectUtil.isEmpty(pendDishesMapper.queryAll(PendDishes.builder().markId(receiveDishes.getPendId())
                .status(1).build())), StatusCode._5035);
        //查询领取菜品记录表中是否今天已添加记录
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(receiveDishesMapper.queryAll(ReceiveDishes.builder().receiveTime(DateUtil.date()).
                userId(receiveDishes.getUserId()).build())), StatusCode._5034);
        //查询第一阶段的菜品等级信息
        DishesStage dishesStage = dishesStageMapper.selectByStage(receiveDishes.getDishesId(), 1);
        //添加菜品领取信息
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        receiveDishes.setMarkId(snowflake.nextIdStr());
        receiveDishes.setReceiveTime(DateUtil.date());
        receiveDishes.setCreateTime(DateUtil.date());
        receiveDishes.setCode(String.valueOf(RandomUtil.randomInt(10000, 99999)));
        receiveDishes.setStageId(dishesStage.getMarkId());
        receiveDishes.setStatus(1);
        receiveDishesMapper.insert(receiveDishes);
        log.info("key：receive_{},录入时间：{}", receiveDishes.getMarkId(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        //dishesStage.getDays() * 24 * 60 * 60
        redis.set("receive_" + receiveDishes.getMarkId(), receiveDishes, 20);
        //更新待领取表的信息状态为已领取
        pendDishesMapper.update(PendDishes.builder().markId(receiveDishes.getPendId()).status(2).modifyTime(DateUtil.date()).build());
        //添加领取菜品的动态记录
        receiveRecordMapper.insert(ReceiveRecord.builder().markId(snowflake.nextIdStr()).userId(receiveDishes.getUserId())
                .description(ReceiveInfo.receiveDish(dishesStage.getDishesName())).createTime(DateUtil.date()).build());
    }

    @Override
    public PageGrid<ReceiveVo> queryReceive(PageParam<ReceiveDishes> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(param.getSidx() + " " + param.getSort());
        PageInfo<ReceiveVo> pageInfo = new PageInfo<>(receiveDishesMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void modifyReceive(ReceiveDishes receiveDishes) {
        receiveDishes.setModifyTime(DateUtil.date());
        receiveDishesMapper.update(receiveDishes);
    }

    @Override
    public void modifyPend(PendDishes pendDishes) {
        pendDishes.setModifyTime(DateUtil.date());
        pendDishesMapper.update(pendDishes);
    }

    @Override
    public List<PendDishes> queryPend(PendDishes pendDishes) {
        return pendDishesMapper.queryAll(pendDishes);
    }

    @Override
    public List<ReceiveRecord> queryRecord(ReceiveRecord receiveRecord) {
        return receiveRecordMapper.queryAll(receiveRecord);
    }

    @Override
    public void modifyRecord(ReceiveRecord receiveRecord) {
        receiveRecordMapper.update(receiveRecord);
    }

    @Override
    public List<ReceiveVo> selectByDish(String markId) {
        return receiveDishesMapper.selectByDish(markId);
    }

    @Override
    public void receiveTicket(String markId) {
        ReceiveVo receiveVo = receiveDishesMapper.queryById(markId);
        if (receiveVo.getStatus() == 1) {
            // 领取菜品优惠券
            Map<String, String> map = new HashMap<>(4);
            map.put("userId", receiveVo.getUserId());
            map.put("templateId", receiveVo.getDishesInfo().getTemplateId());
            sender.memberReceiveTicket(map);
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            //更新领取菜品信息记录（状态改为已领取）
            receiveDishesMapper.update(ReceiveDishes.builder().status(3).markId(receiveVo.getMarkId()).modifyTime(DateUtil.date()).build());
            //添加领取菜品的动态记录
            receiveRecordMapper.insert(ReceiveRecord.builder().markId(snowflake.nextIdStr()).userId(receiveVo.getUserId())
                    .description(ReceiveInfo.template(receiveVo.getDishesInfo().getDishesName())).createTime(DateUtil.date()).build());
        }
    }
}
