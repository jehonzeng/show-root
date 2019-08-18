package com.szhengzhu.service;

import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface SpecialService {

    Result<?> addSpecial(SpecialInfo base);

    Result<PageGrid<SpecialInfo>> getSpecialPage(PageParam<SpecialInfo> base);

    Result<?> editSpecial(SpecialInfo base);

    Result<?> addItemBatchByColumn(SpecialBatchVo base);

    Result<?> addItemBatchByLabel(SpecialBatchVo base);

    Result<PageGrid<SpecialGoodsVo>> getItemPage(PageParam<SpecialItem> base);

    Result<?> deleteItem(SpecialItem base);

    Result<?> sepcialInfoById(String markId);

    Result<?> listSpecialByGoods(String goodsId);

    Result<?> addSpecialItem(SpecialItem base);

    Result<?> addBatchGoods(BatchVo base);
}
