package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.NavVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;

@FeignClient("show-base")
public interface ShowBaseClient {

    @RequestMapping(value = "/images/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<ImageInfo> addImgInfo(@RequestBody ImageInfo imageInfo);
    
    @RequestMapping(value = "/navs/fore/list", method = RequestMethod.GET)
    Result<List<NavVo>> listNavAndItem();
    
    @RequestMapping(value = "/areas/list", method = RequestMethod.GET)
    Result<List<AreaInfo>> listAllArea();
    
    @RequestMapping(value = "/attributes/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listFeedbckType(@RequestParam("type") String type);
    
    @RequestMapping(value = "/feedback/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addFeedback(@RequestBody FeedbackInfo feedbackInfo);
}
