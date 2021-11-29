package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ActivityType;
import com.szhengzhu.bean.activity.ActivityInfo;
import com.szhengzhu.bean.wechat.vo.ActivityGiftVo;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractActivity;
import com.szhengzhu.mapper.ActivityGiftMapper;
import com.szhengzhu.mapper.ActivityInfoMapper;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ActivityType(value = "0")
public class  VoucherActHandler extends AbstractActivity {
    
    @Resource
    private ActivityInfoMapper activityInfoMapper;
    
    @Resource
    private ActivityGiftMapper activityGiftMapper;

    @Override
    public Result<Object> getActBaseInfo(String markId,String userId) {
        ShowAssert.checkTrue(StringUtils.isEmpty(markId), StatusCode._4004);
        Map<String, Object> map = new HashMap<>(2);
        ActivityInfo info = activityInfoMapper.selectByMark(markId);
        List<ActivityGiftVo> gifts = activityGiftMapper.selectGiftsByActId(markId);
        map.put("info", info);
        map.put("gifts", gifts);
        return new Result<>(map);
    }

}
