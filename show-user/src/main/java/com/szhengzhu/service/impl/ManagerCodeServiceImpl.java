package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.user.ManagerCode;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.ManagerCodeMapper;
import com.szhengzhu.mapper.UserInfoMapper;
import com.szhengzhu.service.ManagerCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("managerCodeService")
public class ManagerCodeServiceImpl implements ManagerCodeService {

    @Resource
    private ManagerCodeMapper managerCodeMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public PageGrid<ManagerCode> page(PageParam<ManagerCode> pageParam) {
        PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(pageParam.getSidx() + " " + pageParam.getSort());
        PageInfo<ManagerCode> pageInfo = new PageInfo<>(managerCodeMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void reflush() {
        List<String> managerList = userInfoMapper.selectOutManageCodeList();
        List<ManagerCode> managerCodeList = new LinkedList<>();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        for (String userId : managerList) {
            ManagerCode code = ManagerCode.builder().markId(snowflake.nextIdStr())
                    .userId(userId).code(Contacts.DEFAULT_MANAGER_CODE)
                    .discount(new BigDecimal(1)).serverStatus(false)
                    .build();
            managerCodeList.add(code);
        }
        if (!managerCodeList.isEmpty()) {
            managerCodeMapper.insertBatch(managerCodeList);
        }
    }

    @Override
    public void modify(ManagerCode code) {
        managerCodeMapper.updateByPrimaryKeySelective(code);
    }

    @Override
    public ManagerCode getInfoByCode(String code) {
        return managerCodeMapper.selectByCode(code);
    }
}
