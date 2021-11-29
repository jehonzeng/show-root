package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Identity;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.IdentityEmployeeMapper;
import com.szhengzhu.mapper.IdentityMapper;
import com.szhengzhu.service.IdentityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("identityService'")
public class IdentityServiceImpl implements IdentityService {

    @Resource
    private IdentityMapper identityMapper;

    @Resource
    private IdentityEmployeeMapper identityEmployeeMapper;

    @Override
    public PageGrid<Identity> page(PageParam<Identity> pageParam) {
        String sidx = pageParam.getSidx().equals("mark_id") ? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Identity> pageInfo = new PageInfo<>(
                identityMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Identity getInfo(String identityId) {
        return identityMapper.selectByPrimaryKey(identityId);
    }

    @Override
    public void add(Identity identity) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        identity.setMarkId(snowflake.nextIdStr());
        identity.setCreateTime(DateUtil.date());
        identityMapper.insertSelective(identity);
    }

    @Override
    public void modify(Identity identity) {
        identity.setModifyTime(DateUtil.date());
        identityMapper.updateByPrimaryKeySelective(identity);
    }

    @Override
    public void delete(String identityId) {
        identityMapper.updateStatus(identityId, -1);
    }

    @Override
    public void optEmployee(String[] employeeIds, String identityId) {
        identityEmployeeMapper.deleteByIdentityId(identityId);
        if (employeeIds.length > 0) { identityEmployeeMapper.insertBatch(employeeIds, identityId); }
    }

    @Override
    public String[] listEmployee(String identityId) {
        List<String> employeeIds = identityEmployeeMapper.selectByIdentityId(identityId);
        return employeeIds.toArray(new String[0]);
    }

    @Override
    public String getAuth(String employeeId, String storeId) {
        return identityMapper.selectAuth(employeeId, storeId);
    }

    @Override
    public List<Combobox> listCombobox(String storeId) {
        return identityMapper.selectCombobox(storeId);
    }
}
