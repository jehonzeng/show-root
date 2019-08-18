package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.activity.LinkItem;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.LinkItemMapper;
import com.szhengzhu.mapper.TeambuyInfoMapper;
import com.szhengzhu.service.TeambuyService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;

@Service("teambuyService")
public class TeambuyServiceImpl implements TeambuyService {

    @Resource
    private TeambuyInfoMapper teambuyMapper;

    @Resource
    private LinkItemMapper linkItemMapper;

    @Override
    public Result<PageGrid<TeambuyInfo>> pageTeambuy(PageParam<TeambuyInfo> teambuyPage) {
        PageHelper.startPage(teambuyPage.getPageIndex(), teambuyPage.getPageSize());
        PageHelper.orderBy(teambuyPage.getSidx() + " " + teambuyPage.getSort());
        PageInfo<TeambuyInfo> pageInfo = new PageInfo<>(teambuyMapper.selectByExampleSelective(teambuyPage.getData()));
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Transactional
    @Override
    public Result<TeambuyInfo> saveTeambuy(TeambuyInfo teambuyInfo) {
        if (teambuyInfo == null || StringUtils.isEmpty(teambuyInfo.getProductId()) || teambuyInfo.getType() == null
                || teambuyInfo.getStartTime() == null || teambuyInfo.getStopTime() == null
                || (teambuyInfo.getStartTime().getTime() > teambuyInfo.getStopTime().getTime())
                || teambuyInfo.getReqCount() == null || teambuyInfo.getVaildTime() == null
                || (teambuyInfo.getProductType() == 0 && teambuyInfo.getItems().size() == 0)) {
            return new Result<>(StatusCode._4004);
        }
        IdGenerator generator = IdGenerator.getInstance();
        teambuyInfo.setMarkId(generator.nexId());
        teambuyInfo.setServerStatus(false);
        teambuyMapper.insertSelective(teambuyInfo);
        List<LinkItem> items = teambuyInfo.getItems();
        if (items != null && items.size() > 0) {
            for (int index = 0, size = items.size(); index < size; index++) {
                items.get(index).setMarkId(generator.nexId());
                items.get(index).setSuperId(teambuyInfo.getMarkId());
            }
            linkItemMapper.insertBatch(items);
        }
        return new Result<>(teambuyInfo);
    }

    @Override
    public Result<TeambuyInfo> updateTeambuy(TeambuyInfo teambuyInfo) {
        if (teambuyInfo == null || StringUtils.isEmpty(teambuyInfo.getMarkId())) {
            return new Result<>(StatusCode._4004);
        }
        teambuyMapper.updateByPrimaryKeySelective(teambuyInfo);
        return new Result<>();
    }

    @Override
    public Result<TeambuyInfo> getTeambuyInfo(String markId, String specIds) {
        if (StringUtils.isEmpty(markId))
            return new Result<>(StatusCode._4004);
        TeambuyInfo teambuyInfo = teambuyMapper.selectByKeyAndSpec(markId, specIds);
        return new Result<>(teambuyInfo);
    }

}
