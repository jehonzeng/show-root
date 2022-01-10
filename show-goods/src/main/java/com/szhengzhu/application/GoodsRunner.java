package com.szhengzhu.application;

import com.szhengzhu.mapper.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author Administrator
 */ //@Component
public class GoodsRunner implements ApplicationRunner {

    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    @Resource
    private GoodsVoucherMapper goodsVoucherMapper;

    @Resource
    private MealInfoMapper mealInfoMapper;

    @Resource
    private CookCertifiedMapper cookCertifiedMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception { // 此方案会中途自动删除主键，先不用
//        redisTemplate.delete("goods:goods:ids");
//        List<String> goodsIds = goodsInfoMapper.selectGoodsIds();
////        可选优化方案  使用redis的bitmap，占内存极少
//        for (String goodsId : goodsIds) {
//            long offset = (goodsId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
//            redisTemplate.opsForValue().setBit("goods:goods:ids", offset, true);
//        }
//        if (goodsIds.size() > 0)
//            redisTemplate.expire("goods:goods:ids", 2, TimeUnit.MINUTES);
//        for (String goodsId : goodsIds) {
//            redisTemplate.opsForList().leftPush("goods:goods:ids", goodsId);
//        }

//        redisTemplate.delete("goods:voucher:ids");
//        List<String> voucherIds = goodsVoucherMapper.selectIds();
//        for (String voucherId : voucherIds) {
//            long offset = (voucherId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
//            redisTemplate.opsForValue().setBit("goods:voucher:ids", offset, true);
//        }

//        redisTemplate.delete("goods:meal:ids");
//        List<String> mealIds = mealInfoMapper.selectMealIds();
//        for (String mealId : mealIds) {
//            long offset = (mealId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
//            redisTemplate.opsForValue().setBit("goods:meal:ids", offset, true);
//        }

//        redisTemplate.delete("goods:cooker:ids");
//        List<String> cookerIds = cookCertifiedMapper.selectIds();
//        for (String cookerId : cookerIds) {
//            long offset = (cookerId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
//            redisTemplate.opsForValue().setBit("goods:cooker:ids", offset, true);
//        }

//        redisTemplate.delete("goods:label:ids");
//        List<String> labelIds = labelInfoMapper.selectLabelIds();
//        for (String labelId : labelIds) {
//            long offset = (labelId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
//            redisTemplate.opsForValue().setBit("goods:label:ids", offset, true);
//        }


    }
}
