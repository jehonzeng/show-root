package com.szhengzhu.feign;

import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.base.*;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.vo.*;
import com.szhengzhu.bean.wechat.vo.NavBase;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@FeignClient(name = "show-base", fallback = ExceptionAdvice.class)
public interface ShowBaseClient {

    /** 事件 */
    @PostMapping(value = "/action/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ActionInfo>> pageAction(@RequestBody PageParam<ActionInfo> page);

    @PostMapping(value = "/action/add", consumes = Contacts.CONSUMES)
    Result addAction(@RequestBody ActionInfo actionInfo);

    @PatchMapping(value = "/action/modify", consumes = Contacts.CONSUMES)
    Result modifyAction(@RequestBody ActionInfo actionInfo);

    @PostMapping(value = "/action/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ActionItem>> pageActionItem(@RequestBody PageParam<ActionItem> page);

    @GetMapping(value = "/action/item/{makrId}")
    Result<ActionItem> getActionItemInfo(@PathVariable("makrId") String markId);

    @PostMapping(value = "/action/item/add", consumes = Contacts.CONSUMES)
    Result addActionItem(@RequestBody ActionItem item);

    @GetMapping(value = "/action/item/list")
    Result<List<ActionItem>> listActionItemByCode(@RequestParam("code") String code);

    @PatchMapping(value = "/action/item/modify", consumes = Contacts.CONSUMES)
    Result modifyActionItem(@RequestBody ActionItem item);

    @DeleteMapping(value = "/action/item/{markId}")
    Result deleteActionItem(@PathVariable("markId") String markId);

    /** 区域 */
    @GetMapping(value = "/areas/list")
    Result<List<AreaInfo>> listArea();

    @GetMapping(value = "/areas/list/{version}")
    Result<Map<String, Object>> listAllArea(@PathVariable("version") int version);

    @GetMapping(value = "/areas/listCityAndArea")
    Result<List<AreaVo>> listCityAndArea(@RequestParam("province") String province);

    @GetMapping(value = "/areas/provinces")
    Result<List<Combobox>> listProvince();

    /** 属性 */
    @PostMapping(value = "/attributes/add", consumes = Contacts.CONSUMES)
    Result<AttributeInfo> addAttribute(@RequestBody AttributeInfo attributeInfo);

    @GetMapping(value = "/attributes/combobox")
    Result<List<Combobox>> listComboboxByType(@RequestParam("type") String type);

    @GetMapping(value = "/attributes/getCode")
    Result<String> getCodeByName(@RequestParam("name") String name);

    @GetMapping(value = "/attributes/{markId}")
    Result<AttributeInfo> getAttributeInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/attributes/modify", consumes = Contacts.CONSUMES)
    Result<AttributeInfo> modifyAttribute(@RequestBody AttributeInfo attributeInfo);

    @PostMapping(value = "/attributes/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<AttributeInfo>> pageAttribute(@RequestBody PageParam<AttributeInfo> attrPage);

    /** 券模板 */
    @PostMapping(value = "/coupontemplate/add", consumes = Contacts.CONSUMES)
    Result<CouponTemplate> addTemplate(@RequestBody CouponTemplate couponTemplate);

    @GetMapping(value = "/coupontemplate/list")
    Result<List<Combobox>> listCouponTemplate(@RequestParam("couponType") Integer couponType);

    @GetMapping(value = "/coupontemplate/{markId}")
    Result<CouponTemplate> getCouponTemplateInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/coupontemplate/info")
    Result<CouponTemplate> getCouponTemplate(@RequestParam("templateId") String templateId);

    @PatchMapping(value = "/coupontemplate/modify", consumes = Contacts.CONSUMES)
    Result<CouponTemplate> modfiyTemplate(@RequestBody CouponTemplate couponTemplate);

    @PostMapping(value = "/coupontemplate/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CouponTemplate>> pageTemplate(
            @RequestBody PageParam<CouponTemplate> templatePage);

    /** 反馈 */
    @PostMapping(value = "/feedback/add", consumes = Contacts.CONSUMES)
    Result<Object> addFeedback(@RequestBody FeedbackInfo feedbackInfo);

    @GetMapping(value = "/feedback/backend/index")
    Result<List<IndexDisplay>> getIndexFeedbackCount();

    @PostMapping(value = "/feedback/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<FeedbackInfo>> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage);

    @PatchMapping(value = "/feedback/process", consumes = Contacts.CONSUMES)
    Result processFeedback(@RequestBody FeedbackInfo base);

    /** 图片 */
    @PostMapping(value = "/images/add", consumes = Contacts.CONSUMES)
    Result<ImageInfo> addImgInfo(@RequestBody ImageInfo imageInfo);

    @GetMapping(value = "/images/{markId}")
    Result<ImageInfo> showImage(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/images/{markId}")
    Result deleteImage(@PathVariable("markId") String markId);

    @GetMapping(value = "/images/accessory/{markId}")
    Result<ImageInfo> showAccessorylImage(@RequestParam("markId") String markId);

    @GetMapping(value = "/images/goods/{goodsId}")
    Result<ImageInfo> showGoodsImage(@PathVariable("goodsId") String goodsId,
                                     @RequestParam("type") Integer type,
                                     @RequestParam(value = "specIds", required = false) String specIds);

    @GetMapping(value = "/images/goodSpec/{goodsId}")
    Result<ImageInfo> showGoodsSpecImage(@PathVariable("goodsId") String goodsId,
                                         @RequestParam(value = "specIds", required = false) String specIds);

    @GetMapping(value = "/images/meal/{mealId}")
    Result<ImageInfo> showMealSmallImage(@PathVariable("mealId") String mealId);

    @GetMapping(value = "/images/voucherSpec/{voucherId}")
    Result<ImageInfo> showVoucherSpecImage(@PathVariable("voucherId") String voucherId);

    /** 日志 */
    @PostMapping(value = "/logs/add", consumes = Contacts.CONSUMES)
    Result<LogInfo> createLog(@RequestBody LogInfo base);

    @PostMapping(value = "/logs/login/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Object>> getLoginLogPage(PageParam<LogInfo> base);

    /** 商城首页 */
    @PostMapping(value = "/navs/add", consumes = Contacts.CONSUMES)
    Result<NavInfo> addNav(@RequestBody NavInfo base);

    @PatchMapping(value = "/navs/modify", consumes = Contacts.CONSUMES)
    Result<NavInfo> modifyNav(@RequestBody NavInfo base);

    @PostMapping(value = "/navs/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<NavInfo>> page(@RequestBody PageParam<NavInfo> base);

    @GetMapping(value = "/navs/fore/list")
    Result<List<NavBase>> listNavAndItem();

    @PostMapping(value = "/navs/item", consumes = Contacts.CONSUMES)
    Result<NavItem> addItem(@RequestBody NavItem base);

    @PatchMapping(value = "/navs/item", consumes = Contacts.CONSUMES)
    Result<NavItem> modifyItem(@RequestBody NavItem base);

    @GetMapping(value = "/navs/item/{markId}")
    Result<NavItem> getItem(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/navs/item/{markId}")
    Result deleteItem(@PathVariable("markId") String markId);

    @PostMapping(value = "/navs/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<NavItem>> itemPage(@RequestBody PageParam<NavItem> base);

    /** 券礼包 */
    @PostMapping(value = "/packs/add", consumes = Contacts.CONSUMES)
    Result<PacksInfo> addPacks(@RequestBody PacksInfo base);

    @GetMapping(value = "/packs/{markId}")
    Result<PacksInfo> getPacksInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/packs/update", consumes = Contacts.CONSUMES)
    Result<PacksInfo> modifyPacks(@RequestBody PacksInfo base);

    @GetMapping(value = "/packs/manual")
    Result manualCoupon(@RequestParam("markId")String markId, @RequestParam("userId") String userId);

    @PostMapping(value = "/packs/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PacksInfo>> packsPage(@RequestBody PageParam<PacksInfo> base);

    @PostMapping(value = "/packs/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PacksVo>> packsItemPage(@RequestBody PageParam<PacksItem> base);

    @PostMapping(value = "/packs/item/batch", consumes = Contacts.CONSUMES)
    Result batchPacksTemplate(@RequestBody BatchVo base);

    @PatchMapping(value = "/packs/item/update", consumes = Contacts.CONSUMES)
    Result<PacksItem> updatePacksTeplate(@RequestBody PacksItem base);

    /** 自动回复 */
    @PostMapping(value = "/reply/add", consumes = Contacts.CONSUMES)
    Result addReply(@RequestBody ReplyInfo replyInfo);

    @GetMapping(value = "/reply/info")
    Result<ReplyInfo> getReplyInfoByMsg(@RequestParam("msg") String msg);

    @GetMapping(value = "/reply/{markId}")
    Result<ReplyInfo> getReplyInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/reply/modify", consumes = Contacts.CONSUMES)
    Result modifyReply(@RequestBody ReplyInfo replyInfo);

    @PostMapping(value = "/reply/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ReplyInfo>> pageReply(@RequestBody PageParam<ReplyInfo> page);

    /** 扫码回复 */
    @PostMapping(value = "/scanreply/add", consumes = Contacts.CONSUMES)
    Result addScanReply(@RequestBody ScanReply replyInfo);

    @GetMapping(value = "/scanreply/list/code")
    Result<List<ScanReply>> listScanRelyByCode(@RequestParam("code") String code);

    @GetMapping(value = "/scanreply/{markId}")
    Result<ScanReply> getScanReplyInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/scanreply/modify", consumes = Contacts.CONSUMES)
    Result modifyScanReply(@RequestBody ScanReply replyInfo);

    @PostMapping(value = "/scanreply/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ScanReply>> pageScanReply(@RequestBody PageParam<ScanReply> page);

    /** 门店 */
    @PostMapping(value = "/stores/add", consumes = Contacts.CONSUMES)
    Result<StoreInfo> addStore(@RequestBody StoreInfo base);

    @PatchMapping(value = "/stores/edit", consumes = Contacts.CONSUMES)
    Result<StoreInfo> editStore(@RequestBody StoreInfo base);

    @GetMapping(value = "/stores/list")
    Result<List<Combobox>> listStore();

    @PostMapping(value = "/stores/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<StoreInfo>> pageStore(@RequestBody PageParam<StoreInfo> base);

    @PostMapping(value = "/stores/item/batch", consumes = Contacts.CONSUMES)
    Result<Object> addStoreStaff(@RequestBody BatchVo base);

    @PostMapping(value = "/stores/item/delete", consumes = Contacts.CONSUMES)
    Result deleteStoreStaff(@RequestBody StoreItem base);

    //    @RequestMapping(value = "/stores/item/id", method = RequestMethod.GET)
//    Result<String> getStoreByStaff(@RequestParam("userId") String userId);

    /** 系统配置 */
    @PatchMapping(value = "/sys/modify", consumes = Contacts.CONSUMES)
    Result modifySys(@RequestBody SysInfo sysInfo);

    @GetMapping(value = "/sys/{name}")
    Result<String> getByName(@PathVariable("name") String name);

//    @GetMapping(value = "/sys/refresh")
//    Result<String> refreshMenu(@RequestParam("name") String name);

    /** 官网 */
    @PostMapping(value = "/webs/counsel/add", consumes = Contacts.CONSUMES)
    Result<CounselInfo> addCounsel(@RequestBody CounselInfo base);

    @PostMapping(value = "/webs/counsel/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CounselInfo>> getCounselPage(@RequestBody PageParam<CounselInfo> base);

    @PatchMapping(value = "/webs/content/edit", consumes = Contacts.CONSUMES)
    Result<ProductContent> editProductContent(@RequestBody ProductContent base);

    @GetMapping(value = "/webs/content/{productId}")
    Result<ProductContent> getProductContent(@PathVariable("productId") String productId);



    @PostMapping(value = "/webs/product/add", consumes = Contacts.CONSUMES)
    Result<ProductInfo> addProduct(@RequestBody ProductInfo base);

    @GetMapping(value = "/webs/product/{markId}")
    Result<ProductInfo> getProductInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/webs/product/edit", consumes = Contacts.CONSUMES)
    Result<ProductInfo> editProduct(@RequestBody ProductInfo base);

    @GetMapping(value = "/webs/product/goodsList")
    Result<List<ProductInfo>> getGoodsList();

    @GetMapping(value = "/webs/product/newsInfo")
    Result<NewsVo> getNewsInfo(@RequestParam("markId") String markId);

    @GetMapping(value = "/webs/product/newsList")
    Result<PageGrid<ProductInfo>> getNewList(@RequestParam("pageNo") Integer pageNo,
                                             @RequestParam("pageSize") Integer pageSize);

    @PostMapping(value = "/webs/product/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ProductInfo>> getProductPage(@RequestBody PageParam<ProductInfo> base);


}
