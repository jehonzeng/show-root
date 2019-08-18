package com.szhengzhu.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.base.NavInfo;
import com.szhengzhu.bean.base.NavItem;
import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient(name = "show-base")
public interface ShowBaseClient {

    @RequestMapping(value = "/areas/list", method = RequestMethod.GET)
    Result<List<AreaInfo>> listArea();

    @RequestMapping(value = "/attributes/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<AttributeInfo> addAttribute(@RequestBody AttributeInfo attributeInfo);

    @RequestMapping(value = "/attributes/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<AttributeInfo> modifyAttribute(@RequestBody AttributeInfo attributeInfo);

    @RequestMapping(value = "/attributes/{markId}", method = RequestMethod.GET)
    Result<AttributeInfo> getAttributeInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/attributes/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<AttributeInfo>> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage);

    @RequestMapping(value = "/attributes/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listCombobox(@RequestParam("type") String type);

    @RequestMapping(value = "/coupontemplate/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<CouponTemplate> addTemplate(@RequestBody CouponTemplate couponTemplate);

    @RequestMapping(value = "/coupontemplate/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<CouponTemplate> modfiyTemplate(@RequestBody CouponTemplate couponTemplate);

    @RequestMapping(value = "/coupontemplate/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<CouponTemplate>> pageTemplate(
            @RequestBody PageParam<CouponTemplate> templatePage);

    @RequestMapping(value = "/coupontemplate/info", method = RequestMethod.GET)
    CouponTemplate getCouponTmplate(@RequestParam("templateId") String templateId);

    @RequestMapping(value = "/feedback/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<FeedbackInfo>> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage);

    @RequestMapping(value = "/feedback/process", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> processFeedback(@RequestBody FeedbackInfo base);

    @RequestMapping(value = "/images/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<ImageInfo> addImgInfo(@RequestBody ImageInfo imageInfo);

    @RequestMapping(value = "/images/goods/{goodsId}", method = RequestMethod.GET)
    ImageInfo showGoodsImage(@PathVariable("goodsId") String goodsId,
            @RequestParam("type") Integer type,
            @RequestParam(value = "specIds", required = false) String specIds);

    @RequestMapping(value = "/images/{markId}", method = RequestMethod.GET)
    ImageInfo showImage(@PathVariable("markId") String markId);

    @RequestMapping(value = "/images/delete/{markId}", method = RequestMethod.DELETE)
    void deleteImage(@PathVariable("markId") String markId);

    @RequestMapping(value = "/navs/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<NavInfo> addNav(@RequestBody NavInfo base);

    @RequestMapping(value = "/navs/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<NavInfo> modifyNav(@RequestBody NavInfo base);

    @RequestMapping(value = "/navs/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<NavInfo>> page(@RequestBody PageParam<NavInfo> base);

    @RequestMapping(value = "/navs/item", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<NavItem> addItem(@RequestBody NavItem base);

    @RequestMapping(value = "/navs/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<NavItem>> itemPage(@RequestBody PageParam<NavItem> base);

    @RequestMapping(value = "/navs/item", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<NavItem> modifyItem(@RequestBody NavItem base);

    @RequestMapping(value = "/navs/item/{markId}", method = RequestMethod.GET)
    Result<NavItem> getItem(@PathVariable("markId") String markId);

    @RequestMapping(value = "/navs/item/{markId}", method = RequestMethod.DELETE)
    Result<?> deleteItem(@PathVariable("markId") String markId);

    @RequestMapping(value = "/sys/{name}", method = RequestMethod.GET)
    Result<String> getByName(@PathVariable("name") String name);

    @RequestMapping(value = "/sys/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifySys(@RequestBody SysInfo sysInfo);

    @RequestMapping(value = "/sys/refresh", method = RequestMethod.GET)
    Result<?> refreshMenu(@RequestParam("name") String name);

    @RequestMapping(value = "/areas/provinces", method = RequestMethod.GET)
    Result<List<Combobox>> listProvince();

    @RequestMapping(value = "/coupontemplate/list", method = RequestMethod.GET)
    Result<List<Combobox>> listCouponTempalte(@RequestParam("couponType") Integer couponType);

    @RequestMapping(value = "/images/goodSpec/{goodsId}", method = RequestMethod.GET)
    ImageInfo showGoodsSpecImage(@PathVariable("goodsId") String goodsId,
            @RequestParam(value = "specIds", required = false) String specIds);

    @RequestMapping(value = "/images/voucherSpec/{voucherId}", method = RequestMethod.GET)
    ImageInfo showVoucherSpecImage(@PathVariable("voucherId") String voucherId);

    @RequestMapping(value = "/images/meal/{mealId}", method = RequestMethod.GET)
    ImageInfo showMealSmallImage(@PathVariable("mealId") String mealId);
    
    @RequestMapping(value = "/regions/page", method = RequestMethod.POST,consumes = Contacts.CONSUMES)
    Result<PageGrid<RegionInfo>> pageRegion(@RequestBody PageParam<RegionInfo> base);
    
    @RequestMapping(value = "/regions/add", method = RequestMethod.POST,consumes = Contacts.CONSUMES)
    Result<RegionInfo> addRegion(@RequestBody RegionInfo base );
    
    @RequestMapping(value = "/regions/edit", method = RequestMethod.PATCH,consumes = Contacts.CONSUMES)
    Result<RegionInfo> editRegion(@RequestBody RegionInfo base );

    @RequestMapping(value = "/regions/list", method = RequestMethod.GET)
    Result<List<Combobox>> listRegion();
    
    @RequestMapping(value="/images/accessory/{markId}" ,method = RequestMethod.GET)
    ImageInfo showAccessorylImage(@RequestParam("markId") String markId);

}
