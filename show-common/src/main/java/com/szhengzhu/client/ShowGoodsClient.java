package com.szhengzhu.client;

import com.szhengzhu.bean.excel.MealGoodsModel;
import com.szhengzhu.bean.goods.*;
import com.szhengzhu.bean.vo.*;
import com.szhengzhu.bean.wechat.vo.*;
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
@FeignClient(name = "show-goods", fallback = ExceptionAdvice.class)
public interface ShowGoodsClient {

    /** 附属品管理 */
    @PostMapping(value = "/accessorys/add", consumes = Contacts.CONSUMES)
    Result<AccessoryInfo> addAccessory(@RequestBody AccessoryInfo base);

    @PostMapping(value = "/accessorys/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<AccessoryInfo>> accessoryPage(@RequestBody PageParam<AccessoryInfo> base);

    @PatchMapping(value = "/accessorys/modify", consumes = Contacts.CONSUMES)
    Result<AccessoryInfo> editAccessory(@RequestBody AccessoryInfo base);

    @GetMapping(value = "/accessorys/{markId}")
    Result<AccessoryInfo> getAccessoryInfo(@PathVariable("markId") String markId);

    /** 品牌管理  */
    @PostMapping(value = "/brands/add", consumes = Contacts.CONSUMES)
    Result<BrandInfo> addBrand(@RequestBody BrandInfo brandInfo);

    @PatchMapping(value = "/brands/edit", consumes = Contacts.CONSUMES)
    Result<BrandInfo> modifyBrand(@RequestBody BrandInfo brandInfo);

    @PostMapping(value = "/brands/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<BrandInfo>> getBrandPage(@RequestBody PageParam<BrandInfo> base);

    @GetMapping(value = "/brands/{markId}")
    Result<BrandInfo> getBrandInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/brands/combobox")
    Result<List<Combobox>> listBrandCombobox();



    /** 购物车 */
    @PostMapping(value = "/carts/add", consumes = Contacts.CONSUMES)
    Result<ShoppingCart> addCart(@RequestBody ShoppingCart cart);

    @PatchMapping(value = "/carts/modify", consumes = Contacts.CONSUMES)
    Result<ShoppingCart> modifyCart(@RequestBody ShoppingCart cart);

    @PatchMapping(value = "/carts/refresh", consumes = Contacts.CONSUMES)
    Result refreshCart(@RequestBody CartModel cartModel);

    @GetMapping(value = "/carts/list")
    Result<List<ShoppingCart>> listCart(@RequestParam("userId") String userId);

    @GetMapping(value = "/carts/addition")
    Result<Map<String, Object>> getCartAddition();

    @PostMapping(value = "/carts/calc", consumes = Contacts.CONSUMES)
    Result<CalcData> calcTotal(@RequestBody OrderModel orderModel);

    /** 分类管理 */

    @PostMapping(value = "/categorys/add", consumes = Contacts.CONSUMES)
    Result<CategoryInfo> addCategory(@RequestBody CategoryInfo categoryInfo);

    @PatchMapping(value = "/categorys/edit", consumes = Contacts.CONSUMES)
    Result<CategoryInfo> modifyCategory(@RequestBody CategoryInfo categoryInfo);

    @PostMapping(value = "/categorys/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CategoryInfo>> getCategoryPage(@RequestBody PageParam<CategoryInfo> base);

    @GetMapping(value = "/categorys/{markId}")
    Result<CategoryInfo> getCategoryInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/categorys/downList")
    Result<List<Combobox>> getDwonList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus);

    /** 內容管理 */
    @PostMapping(value = "/columns/add", consumes = Contacts.CONSUMES)
    Result<ColumnInfo> saveColumn(@RequestBody ColumnInfo base);

    @PatchMapping(value = "/columns/update", consumes = Contacts.CONSUMES)
    Result<ColumnInfo> modifyColumn(@RequestBody ColumnInfo base);

    @PostMapping(value = "/columns/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnInfo>> columnPage(@RequestBody PageParam<ColumnInfo> base);

    @GetMapping(value = "/columns/{markId}")
    Result<ColumnInfo> getColumnInfo(@PathVariable(value = "markId") String markId);

    @PostMapping(value = "/columns/goods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnGoodsVo>> columnGoodsPage(@RequestBody PageParam<ColumnGoods> base);

    @PatchMapping(value = "/columns/goods/update", consumes = Contacts.CONSUMES)
    Result<ColumnGoods> updateColumnGoods(@RequestBody ColumnGoods base);

    @PostMapping(value = "/columns/goods/addBatch", consumes = Contacts.CONSUMES)
    Result addBatchColumnGoods(@RequestBody BatchVo base);

    @PostMapping(value = "/columns/goods/delete", consumes = Contacts.CONSUMES)
    Result deleteColumnGoods(@RequestBody ColumnGoods base);

    /** 厨师 */
    @PatchMapping(value = "/cooks/modify", consumes = Contacts.CONSUMES)
    Result<CookCertified> modifyCertified(@RequestBody CookCertified cookCertified);

    @PostMapping(value = "/cooks/add", consumes = Contacts.CONSUMES)
    Result<CookCertified> addCertified(@RequestBody CookCertified cookCertified);

    @PostMapping(value = "/cooks/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<CookCertified>> pageCook(@RequestBody PageParam<CookCertified> cookPage);

    @GetMapping(value = "/cooks/combobox")
    Result<List<Combobox>> listCookerCombobox();

    @GetMapping(value = "/cooks/{markId}")
    Result<CookCertified> getCookById(@PathVariable(value = "markId") String markId);

    @GetMapping(value = "/cooks/sale/rank")
    Result<List<Cooker>> listCookerRank(@RequestParam(value = "userId", required = false) String userId);

    @PostMapping(value = "/cooks/sale/rank/goods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Cooker>> pageCookerRank(@RequestBody PageParam<String> cookerPage);

    @GetMapping(value = "/cooks/detail/{cookerId}")
    Result<Cooker> getCookerDetail(@PathVariable("cookerId") String cookerId, @RequestParam(value = "userId", required = false) String userId);

    @PatchMapping(value = "/cooks/follow/or", consumes = Contacts.CONSUMES)
    Result cookFollowOr(@RequestBody CookFollow cookFollow);

    /** 套餐评论 */

    @PostMapping(value = "/comments/add", consumes = Contacts.CONSUMES)
    Result<MealJudge> addMealJudge(@RequestBody MealJudge judge);

    @PatchMapping(value = "/comments/modify", consumes = Contacts.CONSUMES)
    Result<MealJudge> modifyMealJudge(@RequestBody MealJudge base);

    @PostMapping(value = "/comments/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealJudgeVo>> mealJudgePage(@RequestBody PageParam<MealJudge> base);

    @GetMapping(value = "/comments/fore/list")
    Result<List<JudgeBase>> listMealJudge(@RequestParam("mealId") String mealId, @RequestParam(value = "userId", required = false) String userId);

    /** 商品内容详情  */
    @GetMapping(value = "/contents/show/{goodsId}")
    Result<GoodsContent> showContent(@PathVariable("goodsId") String goodsId);

    @PatchMapping(value = "/contents/edit", consumes = Contacts.CONSUMES)
    Result<GoodsContent> modifyContent(@RequestBody GoodsContent goodsContent);

    /** 仓库配送范围管理 */

    @PostMapping(value = "/delivery/add", consumes = Contacts.CONSUMES)
    Result<DeliveryArea> addDeliveryAreaInfo(@RequestBody DeliveryArea base);

    @PatchMapping(value = "/delivery/modify", consumes = Contacts.CONSUMES)
    Result<DeliveryArea> editDeliveryAreaInfo(@RequestBody DeliveryArea base);

    @PostMapping(value = "/delivery/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<DeliveryArea>> getDeliveryAreaPage(@RequestBody PageParam<DeliveryArea> base);

    @GetMapping(value = "/delivery/{markId}")
    Result<DeliveryArea> deliveryAreaInfo(@PathVariable("markId") String markId);

    @DeleteMapping(value = "/delivery/{markId}")
    Result deleteDelivery(@PathVariable("markId") String markId);

    @PostMapping(value = "/delivery/batch", consumes = Contacts.CONSUMES)
    Result addBatchByProvince(@RequestBody DeliveryArea base);

    @PostMapping(value = "/delivery/area/enabled", consumes = Contacts.CONSUMES)
    Result enabledDeliveryArea(@RequestBody DeliveryArea base);

    @GetMapping(value = "/delivery/price/{addressId}")
    Result<DeliveryArea> getDeliveryPrice(@PathVariable("addressId") String addressId);

    /** 食材管理 */

    @PostMapping(value = "/foods/add", consumes = Contacts.CONSUMES)
    Result<FoodsInfo> addFood(@RequestBody FoodsInfo base);

    @PatchMapping(value = "/foods/edit", consumes = Contacts.CONSUMES)
    Result<FoodsInfo> modifyFood(@RequestBody FoodsInfo base);

    @PostMapping(value = "/foods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<FoodsInfo>> foodPage(@RequestBody PageParam<FoodsInfo> base);

    @GetMapping(value = "/foods/{markId}")
    Result<FoodsInfo> foodsInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/foods/combobox")
    Result<List<Combobox>> listFoodWithoutGoods(@RequestParam("goodsId") String goodsId);

    @GetMapping(value = "/foods/foodCombobox")
    Result<List<Combobox>> listFood();

    @PostMapping(value = "/foods/item/batch", consumes = Contacts.CONSUMES)
    Result addBatchFoodsItem(@RequestBody FoodsItem base);

    @PostMapping(value = "/foods/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsFoodVo>> foodsItemPage(@RequestBody PageParam<FoodsItem> base);

    @DeleteMapping(value = "/foods/item/{markId}")
    Result deleteFoodsItem(@PathVariable("markId") String markId);

    @PatchMapping(value = "/foods/item/modify", consumes = Contacts.CONSUMES)
    Result<FoodsItem> updateFoodsItem(@RequestBody FoodsItem base);

    /** 仓库管理 */
    @PostMapping(value = "/houses/add", consumes = Contacts.CONSUMES)
    Result<StoreHouseInfo> addHouse(@RequestBody StoreHouseInfo storeHouseInfo);

    @GetMapping(value = "/houses/combobox")
    Result<List<Combobox>> listHouseCombobox();

    @PatchMapping(value = "/houses/edit", consumes = Contacts.CONSUMES)
    Result<StoreHouseInfo> modifyHouse(@RequestBody StoreHouseInfo storeHouseInfo);

    @GetMapping(value = "/houses/{markId}")
    Result<StoreHouseInfo> getHouseInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/houses/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<StoreHouseInfo>> getHousePage(@RequestBody PageParam<StoreHouseInfo> base);

    /** 图标 */
    @PostMapping(value = "/icons/add", consumes = Contacts.CONSUMES)
    Result<IconInfo> saveIcon(@RequestBody IconInfo base);

    @PatchMapping(value = "/icons/update", consumes = Contacts.CONSUMES)
    Result<IconInfo> modifyIcon(@RequestBody IconInfo base);

    @PostMapping(value = "/icons/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<IconInfo>> iconPage(@RequestBody PageParam<IconInfo> base);

    @GetMapping(value = "/icons/combobox")
    Result<List<Combobox>> listIconByGoods(@RequestParam("goodsId") String goodsId);

    @GetMapping(value = "/icons/{markId}")
    Result<IconInfo> getIconInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/icons/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<IconGoodsVo>> iconItemPage(@RequestBody PageParam<IconItem> base);

    @PostMapping(value = "/icons/item/delete", consumes = Contacts.CONSUMES)
    Result deleteIconItem(@RequestBody IconItem base);

    @PostMapping(value = "/icons/item/add", consumes = Contacts.CONSUMES)
    Result<IconItem> addIconItem(@RequestBody IconItem base);

    @PostMapping(value = "/icons/goods/addBatch", consumes = Contacts.CONSUMES)
    Result addBatchIconGoods(@RequestBody BatchVo base);

    /** 商品图片管理 */
    @PostMapping(value = "/images/add", consumes = Contacts.CONSUMES)
    Result<GoodsImage> addGoodsImage(@RequestBody GoodsImage base);

    @PatchMapping(value = "/images/edit", consumes = Contacts.CONSUMES)
    Result<GoodsImage> modifyGoodsImage(@RequestBody GoodsImage base);

    @DeleteMapping(value = "/images/delete/{markId}")
    Result deleteGoodsImage(@PathVariable("markId") String markId);

    @GetMapping(value = "/images/list/{goodsId}")
    Result<Map<String, Object>> showGoodsImage(@PathVariable("goodsId") String goodsId,
                                               @RequestParam("serverType") Integer serverType);

    @GetMapping(value = "/images/info")
    Result<GoodsImage> getImageInfo(@RequestParam("markId") String markId);

    /** 商品评论 */

    @PostMapping(value = "/judges/add", consumes = Contacts.CONSUMES)
    Result<GoodsJudge> addGoodsJudge(@RequestBody GoodsJudge judge);

    @PatchMapping(value = "/judges/modify", consumes = Contacts.CONSUMES)
    Result<GoodsJudge> modifyGoodsJudes(@RequestBody GoodsJudge base);

    @PostMapping(value = "/judges/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsJudgeVo>> judgePage(@RequestBody PageParam<GoodsJudge> base);

    @GetMapping(value = "/judges/fore/list")
    Result<List<JudgeBase>> listGoodsJudge(@RequestParam("goodsId") String goodsId, @RequestParam(value = "userId", required = false) String userId);

    /** 商品管理  */
    @PostMapping(value = "/goods/add", consumes = Contacts.CONSUMES)
    Result<GoodsInfo> addGoods(@RequestBody GoodsInfo goodsInfo);

    @GetMapping(value = "/goods/combobox")
    Result<List<Combobox>> listGoodsCombobox();

    @PatchMapping(value = "/goods/edit", consumes = Contacts.CONSUMES)
    Result<GoodsInfo> modifyGoods(@RequestBody GoodsInfo goodsInfo);

    @GetMapping(value = "/goods/{markId}")
    Result<GoodsInfo> goodsInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/goods/listNotColumn")
    Result<List<Combobox>> getListNotColumn();

    @GetMapping(value = "/goods/listNotLabel")
    Result<List<Combobox>> getListNotLabel(@RequestParam("labelId") String labelId);

    @GetMapping(value = "/goods/listNotIcon")
    Result<List<Combobox>> getListNotIcon();

    @PostMapping(value = "/goods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> goodsPage(@RequestBody PageParam<GoodsInfo> base);

    @PatchMapping(value = "/goods/editStatus", consumes = Contacts.CONSUMES)
    Result<GoodsInfo> editGoodsStatus(@RequestBody GoodsInfo base);

    @GetMapping(value = "/goods/fore/detail")
    Result<GoodsDetail> getGoodsDetail(@RequestParam("goodsId") String goodsId,
                                       @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/goods/fore/index/recommend")
    Result<List<GoodsBase>> listRecommend(@RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/goods/servesIn")
    Result<List<String>> serverListInGoods(@RequestParam("goodsId") String goodsId);

    @PostMapping(value = "/goods/addBatchServe", consumes = Contacts.CONSUMES)
    Result addBatchGoodsServe(@RequestBody BatchVo base);

    @PostMapping(value = "/goods/deleteBatchServe", consumes = Contacts.CONSUMES)
    Result deleteBatchGoodsServe(@RequestBody BatchVo base);

    @PostMapping(value = "/goods/column/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByColumn(@RequestBody PageParam<GoodsInfo> base);

    @PostMapping(value = "/goods/icon/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByIcon(@RequestBody PageParam<GoodsInfo> base);

    @PostMapping(value = "/goods/label/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByLabel(@RequestBody PageParam<GoodsInfo> base);

    @PostMapping(value = "/goods/special/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageBySpecial(@RequestBody PageParam<GoodsInfo> base);

    /** 商品规格组合列表  */

    @PostMapping(value = "/goodsSpec/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsSpecification>> pageGoodsSpec(
            @RequestBody PageParam<GoodsSpecification> base);

    @PostMapping(value = "/goodsSpec/add", consumes = Contacts.CONSUMES)
    Result addGoodsSpec(@RequestBody GoodsSpecification base);

    @PatchMapping(value = "/goodsSpec/modify", consumes = Contacts.CONSUMES)
    Result modifyGoodsSpec(@RequestBody GoodsSpecification base);

    /** 商品类型管理 */
    @PostMapping(value = "/goodsTypes/add", consumes = Contacts.CONSUMES)
    Result<GoodsType> addGoodsType(@RequestBody GoodsType base);

    @PatchMapping(value = "/goodsTypes/edit", consumes = Contacts.CONSUMES)
    Result<GoodsType> modifyGoodsType(@RequestBody GoodsType base);

    @PostMapping(value = "/goodsTypes/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsType>> getTypePage(@RequestBody PageParam<GoodsType> base);

    @GetMapping(value = "/goodsTypes/combobox")
    Result<List<Combobox>> listTypeCombobox();

    @PostMapping(value = "/goodsTypes/specification/add")
    Result addTypeSpec(@RequestParam("specIds") String[] specIds,
                       @RequestParam("typeId") String typeId);

    @DeleteMapping(value = "/goodsTypes/specification")
    Result removeTypeSpec(@RequestParam("typeId") String typeId,
                          @RequestParam("specId") String specId);

    @PatchMapping(value = "/goodsTypes/specification/modify", consumes = Contacts.CONSUMES)
    Result modifyTypeSpec(@RequestBody TypeSpec typeSpec);

    /** 菜品券 */

    @PostMapping(value = "/goodsVouchers/add", consumes = Contacts.CONSUMES)
    Result<GoodsVoucher> addGoodsVoucher(@RequestBody GoodsVoucher base);

    @GetMapping(value = "/goodsVouchers/combobox")
    Result<List<Combobox>> listCouponCombobox();

    @PatchMapping(value = "/goodsVouchers/edit", consumes = Contacts.CONSUMES)
    Result<GoodsVoucher> modifyGoodsVoucher(@RequestBody GoodsVoucher base);

    @GetMapping(value = "/goodsVouchers/{markId}")
    Result<GoodsVoucher> getGoodsVoucherInfo(@PathVariable("markId") String markId);

    @PostMapping(value = "/goodsVouchers/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVoucherVo>> getGoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base);

    @GetMapping(value = "/goodsVouchers/sub")
    Result subVoucherStock(@RequestParam("voucherId") String voucherId, @RequestParam("quantity") Integer quantity);

    @GetMapping(value = "/goodsVouchers/add")
    Result addVoucherStock(@RequestParam("voucherId") String voucherId, @RequestParam("quantity") Integer quantity);

    @GetMapping(value = "/goodsVouchers/fore/detail")
    Result<GoodsDetail> getVoucherDetail(@RequestParam("voucherId") String voucherId, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/goodsVouchers/fore/judge")
    Result<List<JudgeBase>> listVoucherJudge(@RequestParam("voucherId") String voucherId, @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/goodsVouchers/stock/info")
    Result<StockBase> getVoucherStock(@RequestParam("voucherId") String voucherId);



    /** 分类标签 */
    @PostMapping(value = "/labels/add", consumes = Contacts.CONSUMES)
    Result<LabelInfo> saveLabel(@RequestBody LabelInfo base);

    @PatchMapping(value = "/labels/update", consumes = Contacts.CONSUMES)
    Result<LabelInfo> modifyLabel(@RequestBody LabelInfo base);

    @PostMapping(value = "/labels/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelInfo>> labelPage(@RequestBody PageParam<LabelInfo> base);

    @PostMapping(value = "/labels/goods/addBatch", consumes = Contacts.CONSUMES)
    Result addBatchLabelGoods(@RequestBody BatchVo base);

    @PostMapping(value = "/labels/goods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelGoodsVo>> labelGoodsPage(@RequestBody PageParam<LabelGoods> base);

    @PatchMapping(value = "/labels/goods/update", consumes = Contacts.CONSUMES)
    Result<LabelGoods> updateLabelGoods(@RequestBody LabelGoods base);

    @PostMapping(value = "/labels/goods/delete", consumes = Contacts.CONSUMES)
    Result deleteLabelGoods(@RequestBody LabelGoods base);

    @GetMapping(value = "/labels/{markId}")
    Result<LabelInfo> getLabelInfo(@PathVariable("markId") String markId);

    @GetMapping(value = "/labels/goods/list")
    Result<List<Label>> listLabelGoods();

    @GetMapping(value = "/labels/{labelId}/product")
    Result<List<GoodsBase>> listLabelGoods(@PathVariable("labelId") String lableId);


    /** 套餐管理 */
    @PostMapping(value = "/meals/add", consumes = Contacts.CONSUMES)
    Result<MealInfo> saveMeal(@RequestBody MealInfo base);

    @PostMapping(value = "/meals/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPage(@RequestBody PageParam<MealInfo> base);

    @PatchMapping(value = "/meals/modify", consumes = Contacts.CONSUMES)
    Result<MealInfo> editMeal(@RequestBody MealInfo base);

    @GetMapping(value = "/meals/combobox")
    Result<List<Combobox>> listMealCombobox();

    @GetMapping(value = "/meals/servesIn")
    Result<List<String>> serverListInMeal(@RequestParam("mealId") String mealId);

    @PostMapping(value = "/meals/addBatchServe", consumes = Contacts.CONSUMES)
    Result addBatchMealServe(BatchVo base);

    @PostMapping(value = "/meals/deleteBatchServe", consumes = Contacts.CONSUMES)
    Result deleteBatchMealServe(BatchVo base);

    @GetMapping(value = "/meals/{markId}")
    Result<MealInfo> getMealById(@PathVariable("markId") String markId);

    @GetMapping(value = "/meals/fore/detail")
    Result<GoodsDetail> getMealDetail(@RequestParam("mealId") String mealId, @RequestParam(value = "userId", required = false) String userId);

    @PostMapping(value = "/meals/item/add", consumes = Contacts.CONSUMES)
    Result<MealItem> saveMealItem(@RequestBody MealItem base);

    @PostMapping(value = "/meals/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealVo>> getMealItemPage(@RequestBody PageParam<MealItem> base);

    @PatchMapping(value = "/meals/item/modify", consumes = Contacts.CONSUMES)
    Result<MealItem> editMealItem(@RequestBody MealItem base);

    @GetMapping(value = "/meals/item/{markId}")
    Result<MealItem> getMealItemById(@PathVariable("markId") String markId);

    @PatchMapping(value = "/mContents/edit", consumes = Contacts.CONSUMES)
    Result<MealContent> editMealContent(@RequestBody MealContent base);

    @GetMapping(value = "/mContents/{mealId}")
    Result<MealContent> getMealContent(@PathVariable("mealId") String mealId);

    @PostMapping(value = "/mImages/add", consumes = Contacts.CONSUMES)
    Result<MealImage> addMealImage(@RequestBody MealImage base);

    @PatchMapping(value = "/mImages/edit", consumes = Contacts.CONSUMES)
    Result<MealImage> modifyMealImage(@RequestBody MealImage base);

    @DeleteMapping(value = "/mImages/{markId}")
    Result<MealImage> deleteMealImage(@PathVariable("markId") String markId);

    @GetMapping(value = "/mImages/list/{mealId}")
    Result<Map<String, Object>> getMealImages(@PathVariable("mealId") String mealId,
                                              @RequestParam("serverType") Integer serverType);

    @GetMapping(value = "/mImages/{markId}")
    Result<MealImage> getMealImageInfo(@PathVariable("markId") String markId);

    /** 套餐库存管理 */
    @PostMapping(value = "/mealstock/add", consumes = Contacts.CONSUMES)
    Result addMealStock(@RequestBody MealStock stock);

    @PatchMapping(value = "/mealstock/modify", consumes = Contacts.CONSUMES)
    Result modifyMealStock(@RequestBody MealStock stock);

    @PostMapping(value = "/mealstock/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealStock>> pageMealStock(@RequestBody PageParam<MealStock> pageStock);

    @PostMapping(value = "/mealstock/current/sub")
    Result subMealCurrentStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/mealstock/total/sub")
    Result subMealTotalStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/mealstock/current/add")
    Result addMealCurrentStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/mealstock/total/add")
    Result addMealTotalStock(@RequestBody ProductInfo productInfo);

    @GetMapping(value = "/mealstock/info")
    Result<StockBase> getMealStock(@RequestParam("mealId") String mealId, @RequestParam(value = "addressId", required = false) String addressId);

    /** 采购管理 */
    @PostMapping(value = "/purchases/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PurchaseFood>> purchasePage(@RequestBody PageParam<PurchaseInfo> base);

    @PostMapping(value = "/purchases/history/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<PurchaseHistoryVo>> purchaseHistoryPage(
            @RequestBody PageParam<PurchaseHistory> base);

    @GetMapping(value = "/purchases/appoin")
    Result appoinStaff(@RequestParam("markId") String markId,
                       @RequestParam("userId") String userId);

    @GetMapping(value = "/purchases/revoke")
    Result revokeStaff(@RequestParam("markId") String markId);

    @PostMapping(value = "/purchases/buy", consumes = Contacts.CONSUMES)
    Result buyFood(@RequestBody PurchaseInfo base);

    @PostMapping(value = "/purchases/create", consumes = Contacts.CONSUMES)
    Result createPurchaseOrder();

    @DeleteMapping(value = "/purchases/clear")
    Result clearPurchaseOrder(@RequestParam("buyTime") String buyTime);

    @GetMapping(value = "/purchases/cheif/product")
    Result<List<MealGoodsModel>> getProductList();

    @PostMapping(value = "/purchases/reflash", consumes = Contacts.CONSUMES)
    Result reflashPurchaseOrder();

    @GetMapping(value = "/purchases/list")
    Result<List<PurchaseFood>> getPurchaseListByStatus(@RequestParam("userId") String userId,
                                                       @RequestParam("status") Integer status);

    // 套餐添加栏目图标等
    @PostMapping(value = "/columns/meal/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnMealVo>> getColumnMealPage(@RequestBody PageParam<ColumnGoods> base);

    @PostMapping(value = "/meals/column/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByColumn(@RequestBody PageParam<MealInfo> base);

    @PostMapping(value = "/labels/meal/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelMealVo>> getLabelMealPage(@RequestBody PageParam<LabelGoods> base);

    @PostMapping(value = "/meals/label/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByLabel(@RequestBody PageParam<MealInfo> base);

    @PostMapping(value = "/meals/special/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base);

    @PostMapping(value = "/meals/icon/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByIcon(@RequestBody PageParam<MealInfo> base);

    /** 服务支持管理 */
    @PostMapping(value = "/serves/add", consumes = Contacts.CONSUMES)
    Result<ServerSupport> addServer(@RequestBody ServerSupport base);

    @PatchMapping(value = "/serves/update", consumes = Contacts.CONSUMES)
    Result<ServerSupport> modifyServer(@RequestBody ServerSupport base);

    @GetMapping(value = "/serves/{markId}")
    Result<ServerSupport> getServeById(@PathVariable("markId") String markId);

    @PostMapping(value = "/serves/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ServerSupport>> serverPage(@RequestBody PageParam<ServerSupport> base);

    @GetMapping(value = "/serves/list")
    Result<List<Combobox>> listServer();

    /** 特价管理 */
    @PostMapping(value = "/specials/add", consumes = Contacts.CONSUMES)
    Result<SpecialInfo> addSpecial(@RequestBody SpecialInfo base);

    @PatchMapping(value = "/specials/modify", consumes = Contacts.CONSUMES)
    Result<SpecialInfo> editSpecial(@RequestBody SpecialInfo base);

    @PostMapping(value = "/specials/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecialInfo>> getSpecialPage(@RequestBody PageParam<SpecialInfo> base);

    @PostMapping(value = "/specials/addBatchByColumn", consumes = Contacts.CONSUMES)
    Result<List<SpecialItem>> addItemBatchByColumn(@RequestBody SpecialBatchVo base);

    @PostMapping(value = "/specials/addBatchByLabel", consumes = Contacts.CONSUMES)
    Result<List<SpecialItem>> addItemBatchByLabel(@RequestBody SpecialBatchVo base);

    @PostMapping(value = "/specials/item/delete", consumes = Contacts.CONSUMES)
    Result deleteSpecialItem(@RequestBody SpecialItem base);

    @PostMapping(value = "/specials/item/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecialGoodsVo>> getSpecialItemPage(@RequestBody PageParam<SpecialItem> base);

    @GetMapping(value = "/specials/{markId}")
    Result<SpecialInfo> specialInfoById(@PathVariable("markId") String markId);

    @GetMapping(value = "/specials/combobox")
    Result<List<Combobox>> listSpecialById(@RequestParam("goodsId") String goodsId);

    @PostMapping(value = "/specials/item/add", consumes = Contacts.CONSUMES)
    Result<SpecialItem> addSpecialItem(@RequestBody SpecialItem base);

    @GetMapping(value = "/goods/listNotSpecial")
    Result<List<Combobox>> getListNotSpecial();

    @PostMapping(value = "/specials/goods/addBatch", consumes = Contacts.CONSUMES)
    Result addBatchSpecialGoods(@RequestBody BatchVo base);

    /** 规格管理  */
    @PostMapping(value = "/specifications/add", consumes = Contacts.CONSUMES)
    Result<SpecificationInfo> addSpecification(@RequestBody SpecificationInfo base);

    @PostMapping(value = "/specifications/addBatch", consumes = Contacts.CONSUMES)
    Result<SpecBatchVo> addBatchSpecification(@RequestBody SpecBatchVo base);

    @GetMapping(value = "/specifications/list")
    Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") String goodsId);

    @PatchMapping(value = "/specifications/modify", consumes = Contacts.CONSUMES)
    Result<SpecificationInfo> modifySpecification(@RequestBody SpecificationInfo base);

    @PostMapping(value = "/specifications/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> getSpecificationPage(
            @RequestBody PageParam<SpecificationInfo> base);

    @PostMapping(value = "/specifications/page/in", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> pageInByType(PageParam<SpecificationInfo> base);

    @PostMapping(value = "/specifications/page/notin", consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> pageNotInByType(PageParam<SpecificationInfo> base);

    /** 库存管理 */
    @PostMapping(value = "/stocks/add", consumes = Contacts.CONSUMES)
    Result<GoodsStock> addGoodsStock(@RequestBody GoodsStock goodsStock);

    @GetMapping(value = "/stocks/info")
    Result<StockBase> getGoodsStcokInfo(@RequestParam("goodsId") String goodsId,
                                        @RequestParam("specIds") String specIds,
                                        @RequestParam(value = "addressId", required = false) String addressId);

    @PatchMapping(value = "/stocks/edit", consumes = Contacts.CONSUMES)
    Result<GoodsStock> modifyGoodsStock(@RequestBody GoodsStock goodsStock);

    @PostMapping(value = "/stocks/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<StockVo>> stockPage(@RequestBody PageParam<GoodsStock> base);

    @GetMapping(value = "/stocks/details/{markId}")
    Result<GoodsBaseVo> details(@PathVariable(value = "markId") String markId);

    @GetMapping(value = "/stocks/list/ids")
    Result<List<StockVo>> listGoodsStocks(@RequestParam("markIds") List<String> markIds);

    @GetMapping(value = "/stocks/{markId}")
    Result<GoodsStock> srtockInfo(@PathVariable(value = "markId") String markId);

    @PostMapping(value = "/stocks/current/sub")
    Result subGoodsCurrentStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/stocks/total/sub")
    Result subGoodsTotalStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/stocks/current/add")
    Result addGoodsCurrentStock(@RequestBody ProductInfo productInfo);

    @PostMapping(value = "/stocks/total/sub")
    Result addGoodsTotalStock(@RequestBody ProductInfo productInfo);
}
