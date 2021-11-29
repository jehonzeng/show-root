package com.szhengzhu.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.activity.ScanWin;
import com.szhengzhu.bean.activity.ScanWinner;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.ScanWinMapper;
import com.szhengzhu.mapper.ScanWinnerMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.ScanWinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jehon Zeng
 */
@Service("scanWinService")
public class ScanWinServiceImpl implements ScanWinService {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private ScanWinMapper scanWinMapper;

    @Resource
    private ScanWinnerMapper scanWinnerMapper;

    private final ConcurrentHashMap<String, Boolean> lockMap = new ConcurrentHashMap<>();

    @Override
    public PageGrid<ScanWin> page(PageParam<ScanWin> page) {
        PageMethod.startPage(page.getPageIndex(), page.getPageSize());
        PageMethod.orderBy(page.getSidx() + " " + page.getSort());
        PageInfo<ScanWin> pageInfo = new PageInfo<>(scanWinMapper.selectByExampleSelective(page.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public ScanWin getInfo(String markId) {
        return scanWinMapper.selectByPrimaryKey(markId);
    }

    @Override
    public void add(ScanWin win) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        win.setMarkId(snowflake.nextIdStr());
        win.setServerStatus(false);
        scanWinMapper.insertSelective(win);
    }

    @Override
    public void modify(ScanWin win) {
        String key = "activity:scanwin:" + win.getMarkId() + ":user";
        String winQueuenKey = "activity:scanwin:queue:" + win.getMarkId();
        redis.del(key);
        redis.del(winQueuenKey);
        scanWinMapper.updateByPrimaryKey(win);
    }

    @Override
    public String scanWin(String scanCode, String openId) {
        ScanWin win = scanWinMapper.selectByCode(scanCode);
        ShowAssert.checkNull(win, StatusCode._4004);
        String key = "activity:scanwin:" + win.getMarkId() + ":user";
        if (redis.hasKey(key)) {
            List<Object> openIds = redis.lGet(key, 0, -1);
            if (openIds.contains(openId)) {
                // 已参与此次抽奖
                return win.getFailMsg();
            }
        }
        int winnerCount = scanWinnerMapper.selectCount(win.getMarkId());
        if (winnerCount >= win.getWinnerTotal()) {
            // 提示未中奖
            return win.getFailMsg();
        }
        long expire = win.getStopTime().getTime() - System.currentTimeMillis();
        String winQueuenKey = "activity:scanwin:queue:" + win.getMarkId();
        if (!redis.hasKey(winQueuenKey)) {
            boolean lock = lockMap.put(scanCode, true) == null;
            if (!lock) {
                // 未中奖
                return win.getFailMsg();
            }
            // 以防超出奖品总数
            int defalutNum;
            if (win.getWinnerTotal() - winnerCount > win.getWinnerNum()) {
                defalutNum = win.getWinnerNum();
            } else {
                defalutNum = win.getWinnerTotal() - winnerCount;
            }
            // 设置几个幸运数字在队列中
            List<Integer> winNumList = new LinkedList<>();
            for (int index = 0; index < defalutNum; index++) {
                int num = RandomUtil.randomInt(win.getLevelNum());
                winNumList.add(num);
            }
            for (int index = 1; index <= win.getLevelNum(); index++) {
                redis.lSet(winQueuenKey, winNumList.contains(index) ? 1 : 0);
            }
            redis.expire(winQueuenKey, expire / 1000);
            lockMap.remove(scanCode);
        }
        Object value = redis.lPop(winQueuenKey);
        redis.lSet(key, openId, expire / 1000);
        if (value != null && !value.equals(1)) {
            return win.getFailMsg();
        }
        sender.scanWinner(openId, win.getProductType().toString(), win.getProductId());
        ScanWinner winner = new ScanWinner();
        winner.setOpenId(openId);
        winner.setWinId(win.getMarkId());
        scanWinnerMapper.insert(winner);
        return win.getSuccessMsg();
    }

}
