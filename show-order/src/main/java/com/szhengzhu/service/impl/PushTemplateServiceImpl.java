package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.order.PushInfo;
import com.szhengzhu.bean.order.PushInfoVo;
import com.szhengzhu.bean.order.PushTemplate;
import com.szhengzhu.bean.order.PushType;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.PushInfoMapper;
import com.szhengzhu.mapper.PushTemplateMapper;
import com.szhengzhu.mapper.PushTypeMapper;
import com.szhengzhu.service.PushTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author makejava
 * @since 2021-07-29 14:35:54
 */
@Service("pushTemplateService")
public class PushTemplateServiceImpl implements PushTemplateService {
    @Resource
    private PushInfoMapper pushInfoMapper;

    @Resource
    private PushTypeMapper pushTypeMapper;

    @Resource
    private PushTemplateMapper pushTemplateMapper;

    @Override
    public PushInfo queryPushInfoById(String markId) {
        return pushInfoMapper.queryById(markId);
    }

    @Override
    public PageGrid<PushTemplate> queryPushTemplateList(PageParam<PushTemplate> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("t.create_time " + param.getSort());
        PageInfo<PushTemplate> pageInfo = new PageInfo<>(pushTemplateMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public PageGrid<PushInfo> queryPushInfoList(PageParam<PushInfo> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<PushInfo> pageInfo = new PageInfo<>(pushInfoMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public List<PushInfo> queryPushInfoByTemplateId(String templateId) {
        return pushInfoMapper.queryAll(PushInfo.builder().templateId(templateId).build());
    }

    @Override
    public void usePushInfo(String markId) {
        PushInfo info = pushInfoMapper.queryById(markId);
        ShowAssert.checkTrue(info.getStatus() == 1, StatusCode._5048);
        pushType(info.getMarkId(), info.getTemplateId(), info.getTypeId(), info.getStatus());
        pushInfoMapper.modify(PushInfo.builder().markId(markId).status(1).build());
    }

    @Override
    public void addPushTemplate(PushInfoVo info) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        pushTemplateMapper.add(PushTemplate.builder().markId(markId).modalId(info.getModalId()).title(info.getTitle()).
                industry(info.getIndustry()).createTime(DateUtil.date()).build());
        pushInfoMapper.add(PushInfo.builder().markId(snowflake.nextIdStr()).templateId(markId).version(1).
                pushInfo(info.getPushInfo()).typeId(info.getTypeId()).createTime(DateUtil.date()).status(1).build());
    }

    @Override
    public void modifyPushTemplate(PushTemplate pushTemplate) {
        pushTemplate.setModifyTime(DateUtil.date());
        pushTemplateMapper.modify(pushTemplate);
    }

    @Override
    public void addPushInfo(PushInfoVo info) {
        Integer version = 0;
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        pushType(markId, info.getTemplateId(), info.getTypeId(), info.getStatus());
        List<PushInfo> pushInfoList = pushInfoMapper.queryAll(PushInfo.builder().templateId(info.getTemplateId()).build());
        for (PushInfo pushInfo : pushInfoList) {
            if (pushInfo.getVersion() > version) {
                version = pushInfo.getVersion();
            }
        }
        pushInfoMapper.add(PushInfo.builder().markId(markId).templateId(info.getTemplateId()).status(info.getStatus()).
                version(version + 1).typeId(info.getTypeId()).pushInfo(info.getPushInfo()).createTime(DateUtil.date()).build());
    }

    @Override
    public void modifyPushInfo(PushInfoVo info) {
        pushType(info.getMarkId(), info.getTemplateId(), info.getTypeId(), info.getStatus());
        pushInfoMapper.modify(PushInfo.builder().markId(info.getMarkId()).templateId(info.getTemplateId()).status(info.getStatus()).
                typeId(info.getTypeId()).modifyTime(DateUtil.date()).pushInfo(info.getPushInfo()).build());
    }

    public void pushType(String markId, String templateId, String typeId, Integer status) {
        Integer count = 0;//模板数量
        Integer num = 0;
        List<PushInfo> pushInfoList = pushInfoMapper.queryAll(PushInfo.builder().templateId(templateId).build());
        for (PushInfo pushInfo : pushInfoList) {
            if (!pushInfo.getMarkId().equals(markId)) {
                if (StrUtil.isNotEmpty(typeId) && StrUtil.isNotEmpty(pushInfo.getTypeId())) {
                    if (pushInfo.getTypeId().equals(typeId) && pushInfo.getStatus().equals(status)) {
                        count += 1;
                    }
                } else {
                    if (pushInfo.getStatus().equals(1)) {
                        num += 1;
                    }
                }
            }
        }
        ShowAssert.checkTrue(num >= 1, StatusCode._5046);
        ShowAssert.checkTrue(count >= 1, StatusCode._5046);
    }

    public void deletePushTemplateById(String markId) {
        pushTemplateMapper.deleteById(markId);
        pushInfoMapper.deleteByTemplateId(markId);
    }

    @Override
    public void deletePushInfoById(String markId) {
        pushInfoMapper.deleteById(markId);
    }

    @Override
    public List<PushType> queryPushTypeList() {
        return pushTypeMapper.queryAll(PushType.builder().build());
    }

    @Override
    public PageGrid<PushType> queryPushTypeByPage(PageParam<PushType> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<PushType> pageInfo = new PageInfo<>(pushTypeMapper.queryAll(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addPushType(PushType pushType) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        pushType.setMarkId(snowflake.nextIdStr());
        pushType.setCreateTime(DateUtil.date());
        pushTypeMapper.add(pushType);
    }

    @Override
    public void deletePushTypeById(String markId) {
        List<PushInfo> pushInfoList = pushInfoMapper.queryAll(PushInfo.builder().typeId(markId).build());
        ShowAssert.checkTrue(ObjectUtil.isNotEmpty(pushInfoList), StatusCode._5047);
        pushTypeMapper.deleteById(markId);
    }

    @Override
    public PushInfo queryPushTemplate(String modelId, String typeId) {
        return pushInfoMapper.queryPushTemplate(modelId, typeId);
    }
}
