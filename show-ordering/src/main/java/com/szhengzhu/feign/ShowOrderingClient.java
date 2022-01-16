package com.szhengzhu.feign;

import com.alibaba.fastjson.JSONObject;
import com.szhengzhu.bean.excel.VoucherCodeExcel;
import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.*;
import com.szhengzhu.bean.ordering.vo.*;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.param.CartParam;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.*;
import com.szhengzhu.code.PayTypeCode;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Terry
 */
@FeignClient(name = "show-ordering", fallback = ExceptionAdvice.class)
public interface ShowOrderingClient {

    /**
     * 预订
     */
    @PostMapping(value = "/booking/selectInfo", consumes = Contacts.CONSUMES)
    Result<List<Booking>> selectInfo(@RequestBody BookingParam booking);

    @PostMapping(value = "/booking/add", consumes = Contacts.CONSUMES)
    Result add(@RequestBody @Validated Booking booking);

    @PatchMapping(value = "/booking/modify", consumes = Contacts.CONSUMES)
    Result modify(@RequestBody Booking booking);

    @PatchMapping(value = "/booking/{markId}")
    Result updateStatus(@PathVariable("markId") @NotBlank String markId);

    @PatchMapping(value = "/booking/useStatus/{markId}")
    Result useStatus(@PathVariable("markId") @NotBlank String markId);

    /**
     * 购物车
     */
    @PostMapping(value = "/cart/add", consumes = Contacts.CONSUMES)
    Result<String> addCart(@RequestBody CartParam cartDetail);

    @PatchMapping(value = "/cart/modify", consumes = Contacts.CONSUMES)
    Result modifyCart(@RequestBody CartParam cartDetail);

    @DeleteMapping(value = "/cart/{tableId}")
    Result clearCart(@PathVariable("tableId") String tableId);

    @GetMapping(value = "/cart/{tableId}")
    Result<Map<String, Object>> listCartByTable(@PathVariable("tableId") String tableId);

    /**
     * 分类
     */
    @PostMapping(value = "/category/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CategoryVo>> pageCate(@RequestBody PageParam<Category> pageParam);

    @PostMapping(value = "/category/add", consumes = Contacts.CONSUMES)
    Result addCate(@RequestBody Category category);

    @PatchMapping(value = "/category/modify", consumes = Contacts.CONSUMES)
    Result modifyCate(@RequestBody Category category);

    @PatchMapping(value = "/category/batch/status/{status}", consumes = Contacts.CONSUMES)
    Result modifyCateStatus(@RequestBody String[] cateIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/category/list/res")
    Result<List<CategoryModel>> listResCate(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/category/list/x")
    Result listLjsCate(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/category/combobox")
    Result<List<Combobox>> getCateCombobox(@RequestParam("storeId") String storeId);

    @PatchMapping(value = "/category/specs/modify", consumes = Contacts.CONSUMES)
    Result modifyCateSpecs(@RequestBody CategorySpecs categorySpecs);

    @PostMapping(value = "/category/specs/batch/opt/{cateId}", consumes = Contacts.CONSUMES)
    Result optCateSpecs(@RequestBody String[] specsIds, @PathVariable("cateId") String cateId);

    @PatchMapping(value = "/category/commodity/modify", consumes = Contacts.CONSUMES)
    Result modifyCateCommodity(@RequestBody CategoryCommodity categoryCommodity);

    @DeleteMapping(value = "/category/commodity/batch/opt/{cateId}", consumes = Contacts.CONSUMES)
    Result optCateCommodity(@RequestBody String[] commodityIds, @PathVariable("cateId") String cateId);

    /**
     * 商品
     */
    @PostMapping(value = "/commodity/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CommodityPageVo>> pageCommodity(@RequestBody PageParam<CommodityParam> pageParam);

    @GetMapping(value = "/commodity/{commodityId}")
    Result<Commodity> getCommodityInfo(@PathVariable("commodityId") String commodityId);

    @GetMapping(value = "/commodity/vo/{commodityId}")
    Result<CommodityVo> getCommodityInfoVo(@PathVariable("commodityId") String commodityId);

    @PostMapping(value = "/commodity/add", consumes = Contacts.CONSUMES)
    Result<String> addCommodity(@RequestBody Commodity commodity);

    @PatchMapping(value = "/commodity/modify", consumes = Contacts.CONSUMES)
    Result modifyCommodity(@RequestBody Commodity commodity);

    @GetMapping(value = "/commodity/modify/quantity")
    Result modifyCommodityQuantity(@RequestParam("commodityId") String commodityId, @RequestParam("employeeId") String employeeId,
                                   @RequestParam("quantity") Integer quantity);

    @PostMapping(value = "/commodity/status/{status}", consumes = Contacts.CONSUMES)
    Result modifyCommodityStatus(@RequestBody String[] commodityIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/commodity/list/by")
    Result<List<Map<String, String>>> listCommodity(@RequestParam(value = "name", required = false) String name);

    @PostMapping(value = "/commodity/price/add", consumes = Contacts.CONSUMES)
    Result<String> addCommodityPrice(@RequestBody CommodityPrice commodityPrice);

    @PatchMapping(value = "/commodity/price/modify", consumes = Contacts.CONSUMES)
    Result modifyCommodityPrice(@RequestBody CommodityPrice commodityPrice);

    @DeleteMapping(value = "/commodity/price/{priceId}")
    Result deleteCommodityPrice(@PathVariable("priceId") String priceId);

    @PostMapping(value = "/commodity/specs/opt", consumes = Contacts.CONSUMES)
    Result optCommoditySpecs(@RequestBody CommoditySpecs commoditySpecs);

    @PostMapping(value = "/commodity/specs/item/opt", consumes = Contacts.CONSUMES)
    Result optCommodityItem(@RequestBody CommodityItem commodityItem);

    @DeleteMapping(value = "/commodity/specs/item/delete")
    Result deleteCommodityItem(@RequestParam("commodityId") String commodityId,
                               @RequestParam("specsId") String specsId, @RequestParam("itemId") String itemId);

    @PostMapping(value = "/commodity/cate/opt/{commodityId}", consumes = Contacts.CONSUMES)
    Result optCommodityCate(@RequestBody String[] cateIds, @PathVariable("commodityId") String commodityId);

    @PostMapping(value = "/commodity/tag/opt/{commodityId}", consumes = Contacts.CONSUMES)
    Result optCommodityTag(@RequestBody String[] tagIds, @PathVariable("commodityId") String commodityId);

    @GetMapping(value = "/commodity/combo")
    Result<List<Commodity>> queryComboCommodity(@RequestParam("markId") String markId);

    /**
     * 商品折扣
     */
    @PostMapping(value = "/discount/add", consumes = Contacts.CONSUMES)
    Result addDiscount(@RequestBody DiscountInfo base);

    @PostMapping(value = "/discount/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<DiscountInfo>> getDiscountPage(@RequestBody PageParam<DiscountInfo> base);

    @GetMapping(value = "/discount/{markId}")
    Result<DiscountInfo> getDiscountInfo(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/discount/{discountId}")
    Result deleteDiscount(@PathVariable("discountId") String discountId);

    @PatchMapping(value = "/discount/modify", consumes = Contacts.CONSUMES)
    Result modifyDiscount(@RequestBody DiscountInfo base);

    @PatchMapping(value = "/discount/batch/{status}", consumes = Contacts.CONSUMES)
    Result modifyDiscountStatus(@RequestBody String[] discountIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/discount/combobox/res")
    Result<List<Combobox>> listDiscountCombobox(@RequestParam("employeeId") String employeeId,
                                                @RequestParam("storeId") String storeId);

    /**
     * 员工
     */
    @PostMapping(value = "/employee/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Employee>> pageEmployee(@RequestBody PageParam<EmployeeParam> pageParam);

    @GetMapping(value = "/employee/{employeeId}")
    Result<Employee> getEmployeeInfo(@PathVariable("employeeId") String employeeId);

    @PostMapping(value = "/employee/add", consumes = Contacts.CONSUMES)
    Result<String> addEmployee(@RequestBody Employee employee);

    @PatchMapping(value = "/employee/modify", consumes = Contacts.CONSUMES)
    Result modifyEmployee(@RequestBody Employee employee);

    @DeleteMapping(value = "/employee/{employeeId}")
    Result deleteEmployee(@PathVariable("employeeId") String employeeId);

    @GetMapping(value = "/employee/p/{phone}")
    Result<Employee> getEmployeeByPhone(@PathVariable("phone") String phone);

    /**
     * 导出Excel
     */
    @GetMapping(value = "/excel/seats/info")
    Result<List<Map<String, Object>>> seatsInfo(@RequestParam("beginDate") Date beginDate, @RequestParam("lastDate") Date lastDate);

    @GetMapping(value = "/excel/seats/typeInfo")
    Result<List<Map<String, Object>>> seatsTypeInfo(@RequestParam("beginDate") Date beginDate, @RequestParam("lastDate") Date lastDate);

    /**
     * 身份角色
     */
    @PostMapping(value = "/identity/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Identity>> pageIdentity(@RequestBody PageParam<Identity> pageParam);

    @GetMapping(value = "/identity/{identityId}")
    Result<Identity> getIdentityInfo(@PathVariable("identityId") String identityId);

    @PostMapping(value = "/identity/add", consumes = Contacts.CONSUMES)
    Result addIdentity(@RequestBody Identity identity);

    @PatchMapping(value = "/identity/modify", consumes = Contacts.CONSUMES)
    Result modifyIdentity(@RequestBody Identity identity);

    @DeleteMapping(value = "/identity/{identityId}")
    Result deleteIdentity(@PathVariable("identityId") String identityId);

//    @RequestMapping(value = "/identity/employee/{identityId}", consumes = Contacts.CONSUMES)
//    Result optIdentityEmployee(@RequestBody String[] employeeIds, @PathVariable("identityId") String identityId);

    @GetMapping(value = "/identity/employee/{identityId}")
    Result<String[]> listIdentityEmployee(@PathVariable("identityId") String identityId);

    @GetMapping(value = "/identity/auth")
    Result<String> getIdentityAuth(@RequestParam("employeeId") String employeeId,
                                   @RequestParam("storeId") String storeId);

    @GetMapping(value = "/identity/combobox")
    Result<List<Combobox>> listIdentityCombobox(@RequestParam("storeId") String storeId);

    /**
     * 订单
     */
    @GetMapping(value = "/indent/info/{indentId}")
    Result<Indent> getIndentInfo(@PathVariable("indentId") String indentId);

    @GetMapping(value = "/indent/info/by/table")
    Result<Indent> getTableInfoByTable(@RequestParam("tableId") String tableId);

    @GetMapping(value = "/indent/status")
    Result modifyIndentStatus(@RequestParam("indentId") String indentId, @RequestParam("status") String status);

    @GetMapping(value = "/indent/user")
    Result<UserIndent> userIndent(@RequestParam("userId") @NotBlank String userId);

    @GetMapping(value = "/indent/model/{indentId}")
    Result<IndentModel> getIndentModel(@PathVariable("indentId") String indentId);

    @PatchMapping(value = "/indent/modify/res", consumes = Contacts.CONSUMES)
    Result modifyDetail(@RequestBody DetailParam detailParam);

    @GetMapping(value = "/indent/bill/{indentId}/res")
    Result billIndent(@PathVariable("indentId") String indentId, @RequestParam("employeeId") String employeeId);

    @GetMapping(value = "/indent/cancel/bill/{indentId}/res")
    Result<String> cancelBillIndent(@PathVariable("indentId") String indentId,
                                    @RequestParam("employeeId") String employeeId);

    @GetMapping(value = "/indent/confirm/res")
    Result confirmIndent(@RequestParam("tableId") String tableId, @RequestParam("employeeId") String employeeId);

    @PostMapping(value = "/indent/create/res", consumes = Contacts.CONSUMES)
    Result createIndentBatch(@RequestBody IndentParam indentParam);

    @GetMapping(value = "/indent/member/bind/res")
    Result bindMember(@RequestParam("indentId") String indentId, @RequestParam("memberId") String memberId);

    @GetMapping(value = "/indent/member/discount")
    Result<BigDecimal> getMemberDiscountTotal(@RequestParam("indentId") @NotBlank String indentId);

    @PostMapping(value = "/indent/page/res", consumes = Contacts.CONSUMES)
    Result<PageGrid<IndentVo>> pageIndent(@RequestBody PageParam<IndentPageParam> pageParam);

    @DeleteMapping(value = "/indent/detail/{detailId}/res")
    Result deleteDetail(@PathVariable("detailId") String detailId, @RequestParam("employeeId") String employeeId);

    @PatchMapping(value = "/indent/detail/discount/res", consumes = Contacts.CONSUMES)
    Result discountDetail(@RequestBody DetailDiscountParam discountParam);

    @GetMapping(value = "/indent/user/list")
    Result<List<String>> listIndentUser(@RequestParam("indentId") String indentId);

    @GetMapping(value = "/indent/select/markId")
    Result<IndentInfo> selectById(@RequestParam("markId") @NotBlank String markId);

    @GetMapping(value = "/indent/detail/list/{indentId}")
    Result<IndentBaseVo> listIndentDetailById(@PathVariable("indentId") String indentId);

//    @GetMapping(value = "/indent/detail/list/{indentId}")
//    Result<?> listIndentDetailById(@PathVariable("indentId") String indentId);

    @GetMapping(value = "/indent/create/{tableId}/x")
    Result<String> createIndent(@PathVariable("tableId") String tableId, @RequestParam("userId") String userId);

    @PostMapping(value = "/indent/page/x", consumes = Contacts.CONSUMES)
    Result<PageGrid<IndentModel>> pageIndentBase(@RequestBody PageParam<String> param);

    @GetMapping(value = "/indent/detail/comm/x")
    Result<List<Map<String, Object>>> listIndentComm(@RequestParam("indentId") String indentId);

    @GetMapping(value = "/indent/cost/total")
    Result<BigDecimal> getIndentCostTotal(@RequestParam("indentId") String indentId);

    /**
     * 订单打印
     */
    @GetMapping(value = "/indent/print/preview")
    Result printPreview(@RequestParam("indentId") String indentId, @RequestParam("employeeId") String employeeId);

    @GetMapping(value = "/indent/print/bill")
    Result printBill(@RequestParam("indentId") String indentId, @RequestParam("employeeId") String employeeId);

    @PostMapping(value = "/indent/print/income")
    Result printIncome(@RequestBody IncomeParam incomeParam);

    /**
     * 订单评论
     */
    @PostMapping(value = "/indent/remark/add")
    Result<Map<String, Object>> addIndentRemark(@RequestBody IndentRemark indentRemark);

    @GetMapping(value = "/indent/remark/{markId}")
    Result<IndentRemark> selectOne(@PathVariable("markId") @NotBlank String markId);

    @PostMapping(value = "/indent/remark/query")
    Result<PageGrid<IndentRemark>> query(@RequestBody PageParam<IndentRemark> param);

    /**
     * 订单支付
     */
    @GetMapping(value = "/indent/pay/{indentPayId}")
    Result<IndentPay> getIndentPayInfo(@PathVariable("indentPayId") String indentPayId);

    @PostMapping(value = "/indent/pay/calc/x", consumes = Contacts.CONSUMES)
    Result<CalcVo> calcIndent(@RequestBody UnifiedIndent indentCalc);

    @PostMapping(value = "/indent/pay/wechat/x", consumes = Contacts.CONSUMES)
    Result<BigDecimal> wechatPay(@RequestBody UnifiedIndent unifiedIndent);

    @PostMapping(value = "/indent/pay/wechat/back/x", consumes = Contacts.CONSUMES)
    Result wechatBack(@RequestParam("indentId") String indentId);

    @PostMapping(value = "/indent/pay/member/x", consumes = Contacts.CONSUMES)
    Result memberPay(@RequestBody UnifiedIndent unifiedIndent);

    @PostMapping(value = "/indent/pay/back", consumes = Contacts.CONSUMES)
    Result addPayBack(@RequestBody PayBack payBack);

    @PostMapping(value = "/indent/pay/refund", consumes = Contacts.CONSUMES)
    Result addRefundBack(@RequestBody PayRefund refund);

    @GetMapping(value = "/indent/pay/refund/no/byid")
    Result<String> getRefundNo(@RequestParam("payId") String payId);

    @GetMapping("/indent/pay/amount/{indentId}")
    Result<BigDecimal> selectAmount(@PathVariable("indentId") String indentId);

    /**
     * 订单支付
     */
    @PostMapping(value = "/indent/pay", consumes = Contacts.CONSUMES)
    Result<String> payIndent(@RequestBody IndentPayParam payParam);

    @DeleteMapping(value = "/indent/pay/{markId}")
    Result deleteIndentPay(@PathVariable("markId") String markId, @RequestParam("employeeId") String employeeId);

    @PostMapping(value = "/indent/pay/"
            + PayTypeCode.COUPON, consumes = Contacts.CONSUMES)
    Result couponPayIndent(@RequestBody IndentPayParam payParam);

    @PostMapping(value = "/indent/pay/"
            + PayTypeCode.VOUCHER, consumes = Contacts.CONSUMES)
    Result voucherPayIndent(@RequestBody IndentPayParam payParam);

    @PostMapping(value = "/indent/pay/"
            + PayTypeCode.TICKET, consumes = Contacts.CONSUMES)
    Result ticketPayIndent(@RequestBody IndentPayParam payParam);

    /**
     * 首页
     */
    @GetMapping(value = "/index/today/turnover")
    Result<Map<String, Object>> todayTurnover(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/index/week/turnover")
    Result<List<Map<String, Object>>> weekTurnover(@RequestParam("storeId") String storeId);

    @PostMapping(value = "/index/today/goodsSalesRank")
    Result<PageGrid<GoodSaleRankParam>> goodsSalesRank(@RequestBody PageParam<?> pageParam, @RequestParam("storeId") String storeId);

    @PostMapping(value = "/index/goodsSale/compare")
    Result<PageGrid<GoodSaleParam>> goodsSaleCompare(@RequestBody PageParam<?> pageParam, @RequestParam("storeId") String storeId);

    @GetMapping(value = "/index/goodsType/sale")
    Result<List<GoodTypeSaleParam>> goodsTypeSale(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/index/today/netReceipts")
    Result<IndexParam> netReceipts(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/index/info")
    Result<Map<String, Object>> info(@RequestParam("storeId") String storeId);

    @PostMapping(value = "/index/goods/sales")
    Result<List<GoodSaleRankParam>> goodsSales(@RequestBody GoodsSales goodsSales);

    @GetMapping(value = "/index/week/netReceipts")
    Result<Map<String, Object>> weekNetReceipts(@RequestParam("storeId") String storeId);

    /**
     * 买减活动
     */
    @PostMapping(value = "/market/page")
    Result<PageGrid<MarketInfo>> pageMarket(@RequestBody PageParam<MarketInfo> pageParam);

    @PostMapping(value = "/market/add")
    Result<String> addMarket(@RequestBody MarketInfo marketInfo);

    @PatchMapping(value = "/market/modify")
    Result modifyMarket(@RequestBody MarketInfo marketInfo);

    @DeleteMapping(value = "/market/{marketId}")
    Result deleteMarket(@PathVariable("marketId") String marketId);

    /**
     * 支付方式
     */
    @PostMapping(value = "/pay/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Pay>> pagePay(@RequestBody PageParam<Pay> pageParam);

    @PostMapping(value = "/pay/add", consumes = Contacts.CONSUMES)
    Result<String> addPay(@RequestBody Pay pay);

    @PatchMapping(value = "/pay/modify", consumes = Contacts.CONSUMES)
    Result modifyPay(@RequestBody Pay pay);

    @DeleteMapping(value = "/pay/{payId}")
    Result deletePay(@PathVariable("payId") String payId);

    @GetMapping(value = "/pay/list/res")
    Result<List<PayBaseVo>> resPayList(@RequestParam("storeId") String storeId);

    @PostMapping(value = "/pay/type/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PayType>> pagePayType(@RequestBody PageParam<PayType> pageParam);

    @PostMapping(value = "/pay/type/add", consumes = Contacts.CONSUMES)
    Result<String> addPayType(@RequestBody PayType payType);

    @PatchMapping(value = "/pay/type/modify", consumes = Contacts.CONSUMES)
    Result modifyPayType(@RequestBody PayType payType);

    @DeleteMapping(value = "/pay/type/{typeId}")
    Result deletePayType(@PathVariable("typeId") String typeId);

    @GetMapping(value = "/pay/type/combobox")
    Result<List<Combobox>> comboboxPayType(@RequestParam("storeId") String storeId);

    /**
     * 打印机
     */
    @PostMapping(value = "/printers/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PrinterVo>> pagePrinter(@RequestBody PageParam<PrinterInfo> base);

    @PostMapping(value = "/printers/add", consumes = Contacts.CONSUMES)
    Result addPrinter(@RequestBody PrinterInfo base);

    @PatchMapping(value = "/printers/modify", consumes = Contacts.CONSUMES)
    Result modifyPrinter(@RequestBody PrinterInfo base);

    @PatchMapping(value = "/printers/batch/{status}", consumes = Contacts.CONSUMES)
    Result modifyPrinterStatus(@RequestBody String[] printerIds, @PathVariable("status") Integer status);

    @PostMapping(value = "/printers/commodity/batch/{printerId}", consumes = Contacts.CONSUMES)
    Result addOrDelBatchCommodity(@RequestBody List<PrinterCommodityVo> commoditys,
                                  @PathVariable("printerId") String printerId);

    @DeleteMapping(value = "/printers/delete/{printerId}")
    Result deletePrinter(@PathVariable("printerId") String printerId);

    @PostMapping(value = "/printers/log/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PrintLogVo>> pagePrintLog(@RequestBody PageParam<PrintLogParam> pageParam);

    @PatchMapping(value = "/printers/log/batch/modify", consumes = Contacts.CONSUMES)
    Result modifyBatchPrintLog(@RequestBody List<PrinterLog> list);

    @PatchMapping(value = "/printers/log/modify", consumes = Contacts.CONSUMES)
    Result modifyPrintLog(@RequestBody PrinterLog log);

    @GetMapping(value = "/printers/log/print/data/{logId}")
    Result<JSONObject> getPrintLogData(@PathVariable("logId") String logId);

    @GetMapping(value = "/printers/log/print/info/{logId}")
    Result getPrintLogInfo(@PathVariable("logId") String logId);

    /**
     * 规格
     */
    @PostMapping(value = "/specs/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Specs>> pageSpecs(@RequestBody PageParam<Specs> pageParam);

    @PostMapping(value = "/specs/add", consumes = Contacts.CONSUMES)
    Result addSpecs(@RequestBody Specs specs);

    @PatchMapping(value = "/specs/modify", consumes = Contacts.CONSUMES)
    Result modifySpecs(@RequestBody Specs specs);

    @PatchMapping(value = "/specs/batch/status/{status}", consumes = Contacts.CONSUMES)
    Result modifySpecsStatus(@RequestBody String[] specsIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/specs/combobox")
    Result<List<SpecsVo>> getSpecsCombobox(@RequestParam("storeId") String storeId);

    @GetMapping(value = "/specs/combobox/cate", consumes = Contacts.CONSUMES)
    Result<List<SpecsVo>> getSpecsComboboxByCateId(@RequestParam(required = false) String[] cateIds, @RequestParam("storeId") String storeId);

    @GetMapping(value = "/specs/list/by")
    Result<List<Map<String, String>>> listSpecs(@RequestParam(value = "name", required = false) String name);

    @PostMapping(value = "/specs/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecsItem>> pageSpecsItem(@RequestBody PageParam<SpecsItem> pageParam);

    @PostMapping(value = "/specs/item/add", consumes = Contacts.CONSUMES)
    Result addSpecsItem(@RequestBody SpecsItem specsItem);

    @PatchMapping(value = "/specs/item/modify", consumes = Contacts.CONSUMES)
    Result modifySpecsItem(@RequestBody SpecsItem specsItem);

    @PatchMapping(value = "/specs/item/batch/status/{status}", consumes = Contacts.CONSUMES)
    Result modifySpecsItemStatus(@RequestBody String[] itemIds, @PathVariable("status") Integer status);

    /**
     * 统计分析
     */
    @PostMapping(value = "/statistics/table/info", consumes = Contacts.CONSUMES)
    Result<List<TableInfoParam>> tableInfo(@RequestBody TableInfo info);

    @PostMapping(value = "/statistics/table/ByTime", consumes = Contacts.CONSUMES)
    Result<List<TableUseParam>> tableByTime(@RequestBody TableInfo info);

    @PostMapping(value = "/statistics/pay/income")
    Result<Map<String, Object>> payIncome(@RequestBody Income income);

    @PostMapping(value = "/statistics/pay/incomeByType")
    Result<List<IncomeByType>> type(@RequestBody Income income);

    @PostMapping(value = "/statistics/amount/compare")
    Result<List<AmountCompare>> amountCompare(@RequestBody TableInfo tableInfo);

    /**
     * 门店
     */
    @PostMapping(value = "/store/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Store>> pageStore(@RequestBody PageParam<StoreParam> pageParam);

    @GetMapping(value = "/store/{storeId}")
    Result<Store> getStoreInfo(@PathVariable("storeId") String storeId);

    @PostMapping(value = "/store/add", consumes = Contacts.CONSUMES)
    Result<String> addStore(@RequestBody Store store);

    @PatchMapping(value = "/store/modify", consumes = Contacts.CONSUMES)
    Result modifyStore(@RequestBody Store store);

    @DeleteMapping(value = "/store/{storeId}")
    Result deleteStore(@PathVariable("storeId") String storeId);

    @GetMapping(value = "/store/employee/map/{employeeId}")
    Result<List<StoreMapVo>> listStoreMapByEmployee(@PathVariable("employeeId") String employeeId);

    @GetMapping(value = "/store/list/x")
    Result<List<StoreModel>> listLjsStore(@RequestParam("city") String city);

    /**
     * 餐桌
     */
    @PostMapping(value = "/table/page")
    Result<PageGrid<TableVo>> pageTable(@RequestBody PageParam<TableParam> pageParam);

    @GetMapping(value = "/table/{tableId}")
    Result<Table> getTableInfo(@PathVariable("tableId") String tableId);

    @PostMapping(value = "/table/add")
    Result<String> addTable(@RequestBody Table table);

    @PatchMapping(value = "/table/modify")
    Result modifyTable(@RequestBody Table table);

    @DeleteMapping(value = "/table/{tableId}")
    Result deleteTable(@PathVariable("tableId") String tableId);

    @GetMapping(value = "/table/info/x")
    Result<TableModel> getLjsTableInfo(@RequestParam("tableId") String tableId);

    @PostMapping(value = "/table/list/res")
    Result<List<TableBaseVo>> listResTableByStore(@RequestBody(required = false) TableReservation tableReservation);

    @GetMapping(value = "/table/change/res")
    Result<List<String>> changeTable(@RequestParam("oldTableId") String oldTableId, @RequestParam("newTableId") String newTableId,
                                     @RequestParam("employeeId") String employeeId);

    @PostMapping(value = "/table/open")
    Result openTable(@RequestBody TableOpenParam param);

    @GetMapping(value = "/table/clear/res")
    Result clearTable(@RequestParam("tableId") String tableId);

    @GetMapping(value = "/table/{storeId}/{tableCode}")
    Result<Table> getTableInfoByStoreCode(@PathVariable("storeId") String storeId, @PathVariable("tableCode") String tableCode);

    @GetMapping(value = "/table/mannum/modify/x")
    Result modifyTableManNum(@RequestParam("tableId") String tableId, @RequestParam("manNum") Integer manNum);

    @GetMapping(value = "/table/id/x")
    Result<String> getTableId(@RequestParam("url") String url);

    /**
     * 餐桌区域
     */
    @PostMapping(value = "/table/area/page")
    Result<PageGrid<TableArea>> pageTableArea(@RequestBody PageParam<TableArea> pageParam);

    @PostMapping(value = "/table/area/add")
    Result<String> addTableArea(@RequestBody TableArea tableArea);

    @PatchMapping(value = "/table/area/modify")
    Result<TableArea> modifyTableArea(@RequestBody TableArea tableArea);

    @DeleteMapping(value = "/table/area/{areaId}")
    Result deleteTableArea(@PathVariable("areaId") String areaId);

    @GetMapping(value = "/table/area/combobox")
    Result<List<Combobox>> listTableAreaCombobox(@RequestParam("storeId") String storeId);

    /**
     * 餐桌类型
     */
    @PostMapping(value = "/table/cls/page")
    Result<PageGrid<TableCls>> pageTableCls(@RequestBody PageParam<TableCls> pageParam);

    @PostMapping(value = "/table/cls/add")
    Result<String> addTableCls(@RequestBody TableCls tableCls);

    @PatchMapping(value = "/table/cls/modify")
    Result modifyTableCls(@RequestBody TableCls tableCls);

    @DeleteMapping(value = "/table/cls/{clsId}")
    Result deleteTableCls(@PathVariable("clsId") String clsId);

    @GetMapping(value = "/table/cls/combobox")
    Result<List<Combobox>> listTableClsCombobox(@RequestParam("storeId") String storeId);

    /**
     * 餐桌状态
     */
    @PostMapping(value = "/table/status/page")
    Result<PageGrid<TableStatus>> pageTableStatus(@RequestBody PageParam<TableStatus> pageParam);

    @PostMapping(value = "/table/status/add")
    Result<TableStatus> addTableStatus(@RequestBody TableStatus tableStatus);

    @PatchMapping(value = "/table/status/modify")
    Result modifyTableStatus(@RequestBody TableStatus tableStatus);

    @DeleteMapping(value = "/table/status/{statusId}")
    Result deleteTableStatus(@PathVariable("statusId") String statusId);

    @GetMapping(value = "/table/status/combobox")
    Result<List<Combobox>> listTableStatusCombobox(@RequestParam("storeId") String storeId);

    /**
     * 标签
     */
    @PostMapping(value = "/tag/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Tag>> pageTag(@RequestBody PageParam<Tag> pageParam);

    @PostMapping(value = "/tag/add", consumes = Contacts.CONSUMES)
    Result addTag(@RequestBody Tag icon);

    @PatchMapping(value = "/tag/modify", consumes = Contacts.CONSUMES)
    Result modifyTag(@RequestBody Tag icon);

    @PatchMapping(value = "/tag/batch/status/{status}", consumes = Contacts.CONSUMES)
    Result modifyTagStatus(@RequestBody String[] iconIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/tag/combobox")
    Result<List<Combobox>> getTagCombobox(@RequestParam("storeId") String storeId);

    @PostMapping(value = "/tag/commodity/batch/add/{tagId}", consumes = Contacts.CONSUMES)
    Result addTagCommodity(@RequestBody String[] commodityIds, @PathVariable("tagId") String tagId);

    @DeleteMapping(value = "/tag/commodity/batch/delete/{tagId}", consumes = Contacts.CONSUMES)
    Result deleteTagCommodity(@RequestBody String[] commodityIds, @PathVariable("tagId") String tagId);

    /**
     * 用户优惠券
     */
    @PostMapping(value = "/template/add", consumes = Contacts.CONSUMES)
    Result addTemplate(@RequestBody TicketTemplate base);

    @PostMapping(value = "/template/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<TicketTemplate>> getTemplatePage(@RequestBody PageParam<TicketTemplate> base);

    @PatchMapping(value = "/template/modify", consumes = Contacts.CONSUMES)
    Result modifyTemplate(@RequestBody TicketTemplate base);

    @GetMapping(value = "/template/{markId}")
    Result<TicketTemplate> getTemplateInfo(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/template/{markId}")
    Result deleteTemplateInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/template/batch/{status}", consumes = Contacts.CONSUMES)
    Result modifyTemplateStatus(@RequestBody String[] templateIds, @PathVariable("status") Integer status);

    @GetMapping(value = "/template/combobox")
    Result<List<Map<String, String>>> getTemplateCombobox();

    /**
     * 用户券
     */
    @GetMapping(value = "/userTicket/list/res")
    Result<List<UserTicketVo>> getUserTicket(@RequestParam("userId") String userId);

    @PostMapping(value = "/userTicket/give/res", consumes = Contacts.CONSUMES)
    Result giveUserTicket(@RequestBody GiveParam giveParam);

    @GetMapping(value = "/userTicket/select/{markId}")
    Result<List<MemberTicket>> memberTicket(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/userTicket/del/{markId}")
    Result deleteMemberTicket(@PathVariable("markId") String markId);

    @GetMapping(value = "/userTicket/list")
    Result<List<UserTicketVo>> listUserTicket(@RequestParam("userId") String userId, @RequestParam(value = "status", required = false) Integer status);

    @GetMapping(value = "/userTicket/list/indent/x")
    Result<List<UserTicketVo>> listUserTicketByIndent(@RequestParam("userId") String userId, @RequestParam("indentId") String indentId);

    @GetMapping(value = "/userTicket/info")
    Result<Integer> queryTicketQuantity(@RequestParam("userId") String userId, @RequestParam("templateId") String templateId);

    /**
     * 代金券
     */
    @PostMapping(value = "/voucher/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Voucher>> pageVoucher(@RequestBody PageParam<Voucher> param);

    @PostMapping(value = "/voucher/add", consumes = Contacts.CONSUMES)
    Result addVoucher(@RequestBody Voucher voucher);

    @PatchMapping(value = "/voucher/modify", consumes = Contacts.CONSUMES)
    Result modifyVoucher(@RequestBody Voucher voucher);

    @GetMapping(value = "/voucher/code/list")
    Result<List<VoucherCodeExcel>> listVoucherCode(@RequestParam("voucherId") String voucherId);

    @GetMapping(value = "/voucher/code/{code}")
    Result<Voucher> getVoucherCodeInfo(@PathVariable("code") String code);

    @GetMapping(value = "/voucher/code/use")
    Result useVoucherCode(@RequestParam String code, @RequestParam Integer amount);

}
