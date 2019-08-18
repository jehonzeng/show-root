package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.PartnerService;

@RestController
@RequestMapping(value = "partners")
public class PartnerController {

    @Resource
    private PartnerService partnerService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addPartner(@RequestBody PartnerInfo base) {
        return partnerService.addPartner(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> editPartner(@RequestBody PartnerInfo base) {
        return partnerService.editPartner(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<?> editPartner(@RequestBody PageParam<PartnerInfo> base) {
        return partnerService.getPartnerPage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.DELETE)
    public Result<?> editPartner(@PathVariable("markId") String markId) {
        return partnerService.deletePartner(markId);
    }
}
