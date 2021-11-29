package com.szhengzhu.service;

import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface SpecialService {

    SpecialInfo addSpecial(SpecialInfo base);

    PageGrid<SpecialInfo> getSpecialPage(PageParam<SpecialInfo> base);

    SpecialInfo editSpecial(SpecialInfo base);

    List<SpecialItem> addItemBatchByColumn(SpecialBatchVo base);

    List<SpecialItem> addItemBatchByLabel(SpecialBatchVo base);

    PageGrid<SpecialGoodsVo> getItemPage(PageParam<SpecialItem> base);

    void deleteItem(SpecialItem base);

    SpecialInfo specialInfoById(String markId);

    List<Combobox> listSpecialByGoods(String goodsId);

    void addSpecialItem(SpecialItem base);

    void addBatchGoods(BatchVo base);
}
