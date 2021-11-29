package com.szhengzhu.service;

import com.szhengzhu.bean.order.PushInfo;
import com.szhengzhu.bean.order.PushInfoVo;
import com.szhengzhu.bean.order.PushTemplate;
import com.szhengzhu.bean.order.PushType;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author makejava
 * @since 2021-07-29 14:35:54
 */
public interface PushTemplateService {

    PushInfo queryPushInfoById(String markId);

    PageGrid<PushTemplate> queryPushTemplateList(PageParam<PushTemplate> param);

    PageGrid<PushInfo> queryPushInfoList(PageParam<PushInfo> param);

    List<PushInfo> queryPushInfoByTemplateId(String templateId);

    void usePushInfo(String markId);

    void addPushTemplate(PushInfoVo pushInfoVo);

    void modifyPushTemplate(PushTemplate pushTemplate);

    void addPushInfo(PushInfoVo pushInfoVo);

    void modifyPushInfo(PushInfoVo pushInfoVo);

    void deletePushTemplateById(String markId);

    void deletePushInfoById(String markId);

    List <PushType> queryPushTypeList();

    PageGrid<PushType> queryPushTypeByPage(PageParam<PushType> param);

    void addPushType(PushType pushType);

    void deletePushTypeById(String markId);

     PushInfo queryPushTemplate(String modelId,String typeId);
}
