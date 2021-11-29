package com.szhengzhu.service;

import com.szhengzhu.bean.activity.GiftInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface GiftService {

    PageGrid<GiftInfo> getGiftPage(PageParam<GiftInfo> base);

    GiftInfo addGift(GiftInfo base);

    GiftInfo updateGift(GiftInfo base);

    GiftInfo getGiftByMark(String markId);

    List<Combobox> getGiftCombobox();

}
