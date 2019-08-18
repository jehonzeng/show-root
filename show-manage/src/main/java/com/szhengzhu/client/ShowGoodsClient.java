package com.szhengzhu.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.ColumnInfo;
import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.FoodsItem;
import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.goods.IconInfo;
import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.goods.LabelInfo;
import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.goods.PurchaseHistory;
import com.szhengzhu.bean.goods.PurchaseInfo;
import com.szhengzhu.bean.goods.ServerSupport;
import com.szhengzhu.bean.goods.SpecialInfo;
import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.GoodsFoodVo;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.vo.IconGoodsVo;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.bean.vo.PurchaseFood;
import com.szhengzhu.bean.vo.PurchaseHistoryVo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.bean.vo.SpecialBatchVo;
import com.szhengzhu.bean.vo.SpecialGoodsVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient(name = "show-goods")
public interface ShowGoodsClient {
    /**
     * 品牌管理
     * 
     * @date 2019年4月2日 上午10:30:58
     * @param brandInfo
     * @return
     */

    @RequestMapping(value = "/brands/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<BrandInfo> addBrand(@RequestBody BrandInfo brandInfo);

    @RequestMapping(value = "/brands/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<BrandInfo> modifyBrand(@RequestBody BrandInfo brandInfo);

    @RequestMapping(value = "/brands/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<BrandInfo>> getBrandPage(@RequestBody PageParam<BrandInfo> base);

    @RequestMapping(value = "/brands/{markId}", method = RequestMethod.GET)
    Result<BrandInfo> getBrandInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/brands/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listBrandCombobox();

    /**
     * 分类管理
     * 
     * @date 2019年4月2日 上午10:31:02
     * @param categoryInfo
     * @return
     */

    @RequestMapping(value = "/categorys/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<CategoryInfo> addCategory(@RequestBody CategoryInfo categoryInfo);

    @RequestMapping(value = "/categorys/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<CategoryInfo> modifyCategory(@RequestBody CategoryInfo categoryInfo);

    @RequestMapping(value = "/categorys/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<CategoryInfo>> getCategoryPage(@RequestBody PageParam<CategoryInfo> base);

    @RequestMapping(value = "/categorys/{markId}", method = RequestMethod.GET)
    Result<CategoryInfo> getCategoryInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/categorys/downList", method = RequestMethod.GET)
    Result<List<Combobox>> getDwonList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus);

    @RequestMapping(value = "/categorys/superList", method = RequestMethod.GET)
    Result<List<Combobox>> getSuperList();

    /**
     * 仓库管理
     * 
     * @date 2019年4月2日 上午10:31:11
     * @param storeHouseInfo
     * @return
     */
    @RequestMapping(value = "/houses/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<StoreHouseInfo> addHouse(@RequestBody StoreHouseInfo storeHouseInfo);

    @RequestMapping(value = "/houses/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<StoreHouseInfo> modifyHouse(@RequestBody StoreHouseInfo storeHouseInfo);

    @RequestMapping(value = "/houses/{markId}", method = RequestMethod.GET)
    Result<StoreHouseInfo> getHouseInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/houses/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<StoreHouseInfo>> getHousePage(@RequestBody PageParam<StoreHouseInfo> base);

    /**
     * 商品管理
     * 
     * @date 2019年4月2日 上午10:31:59
     * @param goodsInfo
     * @return
     */
    @RequestMapping(value = "/goods/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsInfo> modifyGoods(@RequestBody GoodsInfo goodsInfo);

    @RequestMapping(value = "/goods/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<GoodsInfo> addGoods(@RequestBody GoodsInfo goodsInfo);

    @RequestMapping(value = "/goods/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> goodsPage(@RequestBody PageParam<GoodsInfo> base);

    @RequestMapping(value = "/goods/{markId}", method = RequestMethod.GET)
    Result<GoodsInfo> goodsInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/goods/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listGoodsCombobox();

    @RequestMapping(value = "/goods/editStatus", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsInfo> editGoodsStatus(@RequestBody GoodsInfo base);

    /**
     * 商品内容详情
     * 
     * @date 2019年4月24日 下午4:49:21
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/contents/show/{goodsId}", method = RequestMethod.GET)
    Result<GoodsContent> showContent(@PathVariable("goodsId") String goodsId);

    @RequestMapping(value = "/contents/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsContent> modifyContent(@RequestBody GoodsContent goodsContent);

    /**
     * 商品规格组合列表
     * 
     * @date 2019年6月24日 下午4:30:49
     * @param base
     * @return
     */
    @RequestMapping(value = "/goodsSpec/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsSpecification>> pageGoodsSpec(
            @RequestBody PageParam<GoodsSpecification> base);

    @RequestMapping(value = "/goodsSpec/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addGoosSpec(@RequestBody GoodsSpecification base);

    @RequestMapping(value = "/goodsSpec/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifyGoodsSpec(@RequestBody GoodsSpecification base);

    /**
     * 规格管理
     * 
     * @date 2019年4月2日 上午10:32:13
     * @param goodsSpecification
     * @return
     */
    @RequestMapping(value = "/specifications/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SpecificationInfo> addSpecification(@RequestBody SpecificationInfo base);

    @RequestMapping(value = "/specifications/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<SpecificationInfo> modifySpecification(@RequestBody SpecificationInfo base);

    @RequestMapping(value = "/specifications/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> getSpecificationPage(
            @RequestBody PageParam<SpecificationInfo> base);

    @RequestMapping(value = "/specifications/list", method = RequestMethod.GET)
    Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") String goodsId);

    @RequestMapping(value = "/specifications/addBatch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SpecBatchVo> addBatchSpecification(@RequestBody SpecBatchVo base);

    @RequestMapping(value = "/specifications/page/in", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> pageInByType(PageParam<SpecificationInfo> base);

    @RequestMapping(value = "/specifications/page/notin", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecificationInfo>> pageNotInByType(PageParam<SpecificationInfo> base);

    /**
     * 库存管理
     * 
     * @date 2019年4月2日 上午10:32:27
     * @param goodsStock
     * @return
     */
    @RequestMapping(value = "/stocks/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<GoodsStock> addGoodsStock(@RequestBody GoodsStock goodsStock);

    @RequestMapping(value = "/stocks/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsStock> modifyGoodsStock(@RequestBody GoodsStock goodsStock);

    @RequestMapping(value = "/stocks/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<StockVo>> stockPage(@RequestBody PageParam<GoodsStock> base);

    @RequestMapping(value = "/stocks/details/{markId}", method = RequestMethod.GET)
    Result<GoodsBaseVo> details(@PathVariable(value = "markId") String markId);

    @RequestMapping(value = "/stocks/{markId}", method = RequestMethod.GET)
    Result<GoodsStock> srtockInfo(@PathVariable(value = "markId") String markId);

    /**
     * 商品图片管理
     * 
     * @date 2019年4月2日 上午10:32:40
     * @param base
     * @return
     */
    @RequestMapping(value = "/images/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<GoodsImage> addGoodsImage(@RequestBody GoodsImage base);

    @RequestMapping(value = "/images/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsImage> modifyGoodsImage(@RequestBody GoodsImage base);

    @RequestMapping(value = "/images/delete/{markId}", method = RequestMethod.DELETE)
    Result<GoodsImage> deleteGoodsImage(@PathVariable("markId") String markId);

    @RequestMapping(value = "/images/list/{goodsId}", method = RequestMethod.GET)
    Result<Map<String, Object>> showGoodsImage(@PathVariable("goodsId") String goodsId,
            @RequestParam("serverType") Integer serverType);

    @RequestMapping(value = "/images/info", method = RequestMethod.GET)
    GoodsImage getImageInfo(@RequestParam("markId") String markId);

    /**
     * 商品类型管理
     * 
     * @date 2019年4月2日 上午10:33:01
     * @param base
     * @return
     */
    @RequestMapping(value = "/goodsTypes/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<GoodsType> addGoodsType(@RequestBody GoodsType base);

    @RequestMapping(value = "/goodsTypes/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsType> modifyGoodsType(@RequestBody GoodsType base);

    @RequestMapping(value = "/goodsTypes/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsType>> getTypePage(@RequestBody PageParam<GoodsType> base);

    @RequestMapping(value = "/goodsTypes/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listTypeCombobox();

    @RequestMapping(value = "/goodsTypes/specification/add", method = RequestMethod.POST)
    Result<?> addTypeSpec(@RequestParam("specIds") String[] specIds,
            @RequestParam("typeId") String typeId);

    @RequestMapping(value = "/goodsTypes/specification", method = RequestMethod.DELETE)
    Result<?> removeTypeSpec(@RequestParam("typeId") String typeId,
            @RequestParam("specId") String specId);

    @RequestMapping(value = "/goodsTypes/specification/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> modifyTypeSpec(@RequestBody TypeSpec typeSpec);

    /**
     * 服务支持管理
     * 
     * @date 2019年4月2日 上午10:35:05
     * @param base
     * @return
     */
    @RequestMapping(value = "/serves/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<ServerSupport> addServer(@RequestBody ServerSupport base);

    @RequestMapping(value = "/serves/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<ServerSupport> modifyServer(@RequestBody ServerSupport base);

    @RequestMapping(value = "/serves/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<ServerSupport>> serverPage(@RequestBody PageParam<ServerSupport> base);

    @RequestMapping(value = "/serves/list", method = RequestMethod.GET)
    Result<List<Combobox>> listServer();

    @RequestMapping(value = "/serves/{markId}", method = RequestMethod.GET)
    Result<ServerSupport> getServeById(@PathVariable("markId") String markId);

    @RequestMapping(value = "/goods/servesIn", method = RequestMethod.GET)
    Result<List<String>> serverListInGoods(@RequestParam("goodsId") String goodsId);

    @RequestMapping(value = "/goods/addBatchServe", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchGoodsServe(@RequestBody BatchVo base);

    @RequestMapping(value = "/goods/deleteBatchServe", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteBatchGoodsServe(@RequestBody BatchVo base);

    @RequestMapping(value = "/meals/servesIn", method = RequestMethod.GET)
    Result<List<String>> serverListInMeal(@RequestParam("mealId") String mealId);

    @RequestMapping(value = "/meals/addBatchServe", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchMealServe(BatchVo base);

    @RequestMapping(value = "/meals/deleteBatchServe", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteBatchMealServe(BatchVo base);

    /**
     * 商品券管理
     * 
     * @date 2019年4月2日 上午10:33:18
     * @param base
     * @return
     */
    @RequestMapping(value = "/goodsVouchers/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<GoodsVoucher> addGoodsVoucher(@RequestBody GoodsVoucher base);

    @RequestMapping(value = "/goodsVouchers/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsVoucher> modifyGoodsVoucher(@RequestBody GoodsVoucher base);

    @RequestMapping(value = "/goodsVouchers/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVoucherVo>> getGoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base);

    @RequestMapping(value = "/goodsVouchers/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listCouponCombobox();

    @RequestMapping(value = "/goodsVouchers/{markId}", method = RequestMethod.GET)
    Result<GoodsVoucher> goodsVoucherInfo(@PathVariable("markId") String markId);

    /**
     * 仓库配送范围管理
     * 
     * @date 2019年4月2日 上午10:34:12
     * @param base
     * @return
     */
    @RequestMapping(value = "/delivery/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<DeliveryArea> addDeliveryAreaInfo(@RequestBody DeliveryArea base);

    @RequestMapping(value = "/delivery/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<DeliveryArea> editDeliveryAreaInfo(@RequestBody DeliveryArea base);

    @RequestMapping(value = "/delivery/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<DeliveryArea>> getDeliveryAreaPage(@RequestBody PageParam<DeliveryArea> base);

    @RequestMapping(value = "/delivery/{markId}", method = RequestMethod.GET)
    Result<DeliveryArea> deliveryAreaInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/delivery/{markId}", method = RequestMethod.DELETE)
    Result<?> deleteDelivery(@PathVariable("markId") String markId);

    @RequestMapping(value = "/delivery/batch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchByProvince(@RequestBody DeliveryArea base);

    @RequestMapping(value = "/houses/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listHouseCombobox();

    /**
     * 食材管理
     * 
     * @date 2019年4月2日 上午10:30:35
     * @param base
     * @return
     */

    @RequestMapping(value = "/foods/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<FoodsInfo> addFood(@RequestBody FoodsInfo base);

    @RequestMapping(value = "/foods/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<FoodsInfo> modifyFood(@RequestBody FoodsInfo base);

    @RequestMapping(value = "/foods/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<FoodsInfo>> foodPage(@RequestBody PageParam<FoodsInfo> base);

    @RequestMapping(value = "/foods/{markId}", method = RequestMethod.GET)
    Result<FoodsInfo> foodsInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/foods/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listFoodWithoutGoods(@RequestParam("goodsId") String goodsId);

    @RequestMapping(value = "/foods/foodCombobox", method = RequestMethod.GET)
    Result<List<Combobox>> listFood();

    @RequestMapping(value = "/foods/item/batch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchFoodsItem(@RequestBody FoodsItem base);

    @RequestMapping(value = "/foods/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsFoodVo>> foodsItemPage(@RequestBody PageParam<FoodsItem> base);

    @RequestMapping(value = "/foods/item/{markId}", method = RequestMethod.DELETE)
    Result<?> deleteFoodsItem(@PathVariable("markId") String markId);

    @RequestMapping(value = "/foods/item/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<FoodsItem> updateFoodsItem(@RequestBody FoodsItem base);

    /**
     * 商品评论
     * 
     * @date 2019年4月24日 下午4:47:46
     * @param base
     * @return
     */

    @RequestMapping(value = "/judges/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<GoodsJudge> modifyGoodsJudes(@RequestBody GoodsJudge base);

    @RequestMapping(value = "/judges/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsJudgeVo>> judgePage(@RequestBody PageParam<GoodsJudge> base);

    /**
     * 內容管理
     * 
     * @date 2019年3月29日 下午4:06:08
     * @param base
     * @return
     */

    @RequestMapping(value = "/columns/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<ColumnInfo> saveColumn(@RequestBody ColumnInfo base);

    @RequestMapping(value = "/columns/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<ColumnInfo> modifyColumn(@RequestBody ColumnInfo base);

    @RequestMapping(value = "/columns/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnInfo>> columnPage(@RequestBody PageParam<ColumnInfo> base);

    @RequestMapping(value = "/columns/{markId}", method = RequestMethod.GET)
    Result<ColumnInfo> getColumnInfo(@PathVariable(value = "markId") String markId);

    @RequestMapping(value = "/columns/goods/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnGoodsVo>> columnGoodsPage(@RequestBody PageParam<ColumnGoods> base);

    @RequestMapping(value = "/columns/goods/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<ColumnGoods> updateColumnGoods(@RequestBody ColumnGoods base);

    @RequestMapping(value = "/columns/goods/addBatch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchColumnGoods(@RequestBody BatchVo base);

    @RequestMapping(value = "/columns/goods/delete", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteColumnGoods(@RequestBody ColumnGoods base);

    @RequestMapping(value = "/labels/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<LabelInfo> saveLabel(@RequestBody LabelInfo base);

    @RequestMapping(value = "/labels/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<LabelInfo> modifyLabel(@RequestBody LabelInfo base);

    @RequestMapping(value = "/labels/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelInfo>> labelPage(@RequestBody PageParam<LabelInfo> base);

    @RequestMapping(value = "/labels/goods/addBatch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchLabelGoods(@RequestBody BatchVo base);

    @RequestMapping(value = "/labels/goods/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelGoodsVo>> labelGoodsPage(@RequestBody PageParam<LabelGoods> base);

    @RequestMapping(value = "/labels/goods/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<LabelGoods> updateLabelGoods(@RequestBody LabelGoods base);

    @RequestMapping(value = "/labels/goods/delete", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteLabelGoods(@RequestBody LabelGoods base);

    @RequestMapping(value = "/labels/{markId}", method = RequestMethod.GET)
    Result<LabelInfo> getLabelInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/goods/listNotColumn", method = RequestMethod.GET)
    Result<List<Combobox>> getListNotColumn();

    @RequestMapping(value = "/goods/listNotLabel", method = RequestMethod.GET)
    Result<List<Combobox>> getListNotLabel(@RequestParam("labelId") String labelId);

    @RequestMapping(value = "/icons/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<IconInfo> saveIcon(@RequestBody IconInfo base);

    @RequestMapping(value = "/icons/update", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<IconInfo> modifyIcon(@RequestBody IconInfo base);

    @RequestMapping(value = "/icons/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<IconInfo>> iconPage(@RequestBody PageParam<IconInfo> base);

    @RequestMapping(value = "/icons/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listIconByGoods(@RequestParam("goodsId") String goodsId);

    @RequestMapping(value = "/icons/{markId}", method = RequestMethod.GET)
    Result<IconInfo> getIconInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/icons/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<IconGoodsVo>> iconItemPage(@RequestBody PageParam<IconItem> base);

    @RequestMapping(value = "/icons/item/delete", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteIconItem(@RequestBody IconItem base);

    @RequestMapping(value = "/icons/item/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<IconItem> addIconItem(@RequestBody IconItem base);

    @RequestMapping(value = "/goods/listNotIcon", method = RequestMethod.GET)
    Result<List<Combobox>> getListNotIcon();

    @RequestMapping(value = "/icons/goods/addBatch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchIconGoods(@RequestBody BatchVo base);

    /**
     * 套餐管理
     * 
     * @date 2019年4月19日 下午4:30:58
     * @param base
     * @return
     */

    @RequestMapping(value = "/meals/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<MealInfo> saveMeal(@RequestBody MealInfo base);

    @RequestMapping(value = "/meals/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPage(@RequestBody PageParam<MealInfo> base);

    @RequestMapping(value = "/meals/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<MealInfo> editMeal(@RequestBody MealInfo base);

    @RequestMapping(value = "/meals/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listMealCombobox();

    @RequestMapping(value = "/meals/item/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<MealItem> saveMealItem(@RequestBody MealItem base);

    @RequestMapping(value = "/meals/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealVo>> getMealItemPage(@RequestBody PageParam<MealItem> base);

    @RequestMapping(value = "/meals/item/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<MealItem> editMealItem(@RequestBody MealItem base);

    @RequestMapping(value = "/meals/{markId}", method = RequestMethod.GET)
    Result<MealInfo> getMealById(@PathVariable("markId") String markId);

    @RequestMapping(value = "/meals/item/{markId}", method = RequestMethod.GET)
    Result<MealItem> getMealItemById(@PathVariable("markId") String markId);

    @RequestMapping(value = "/mContents/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<MealContent> editMealContent(@RequestBody MealContent base);

    @RequestMapping(value = "/mContents/{mealId}", method = RequestMethod.GET)
    Result<MealContent> getMealContent(@PathVariable("mealId") String mealId);

    @RequestMapping(value = "/mImages/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<MealImage> addMealImage(@RequestBody MealImage base);

    @RequestMapping(value = "/mImages/edit", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<MealImage> modifyMealImage(@RequestBody MealImage base);

    @RequestMapping(value = "/mImages/{markId}", method = RequestMethod.DELETE)
    Result<MealImage> deleteMealImage(@PathVariable("markId") String markId);

    @RequestMapping(value = "/mImages/list/{mealId}", method = RequestMethod.GET)
    Result<Map<String, Object>> getMealImages(@PathVariable("mealId") String mealId,
            @RequestParam("serverType") Integer serverType);

    @RequestMapping(value = "/mImages/{markId}", method = RequestMethod.GET)
    MealImage getMealImageInfo(@PathVariable("markId") String markId);

    @RequestMapping(value = "/comments/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<MealJudge> modifyMealJudge(@RequestBody MealJudge base);

    @RequestMapping(value = "/comments/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealJudgeVo>> mealJudgePage(@RequestBody PageParam<MealJudge> base);

    /**
     * 特价管理
     * 
     * @date 2019年4月28日 上午11:43:04
     * @param base
     * @return
     */
    @RequestMapping(value = "/specials/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SpecialInfo> addSpecial(@RequestBody SpecialInfo base);

    @RequestMapping(value = "/specials/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<SpecialInfo> editSpecial(@RequestBody SpecialInfo base);

    @RequestMapping(value = "/specials/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecialInfo>> getSpecialPage(@RequestBody PageParam<SpecialInfo> base);

    @RequestMapping(value = "/specials/addBatchByColumn", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addItemBatchByColumn(@RequestBody SpecialBatchVo base);

    @RequestMapping(value = "/specials/addBatchByLabel", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addItemBatchByLabel(@RequestBody SpecialBatchVo base);

    @RequestMapping(value = "/specials/item/delete", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> deleteSpecialItem(@RequestBody SpecialItem base);

    @RequestMapping(value = "/specials/item/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<SpecialGoodsVo>> getSpecialItemPage(@RequestBody PageParam<SpecialItem> base);

    @RequestMapping(value = "/specials/{markId}", method = RequestMethod.GET)
    Result<SpecialInfo> specialInfoById(@PathVariable("markId") String markId);

    @RequestMapping(value = "/specials/combobox", method = RequestMethod.GET)
    Result<List<Combobox>> listSpecialById(@RequestParam("goodsId") String goodsId);

    @RequestMapping(value = "/specials/item/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<SpecialItem> addSpecialItem(@RequestBody SpecialItem base);

    @RequestMapping(value = "/goods/listNotSpecial", method = RequestMethod.GET)
    Result<List<Combobox>> getListNotSpecial();

    @RequestMapping(value = "/specials/goods/addBatch", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addBatchSpecialGoods(@RequestBody BatchVo base);

    /**
     * 附属品管理
     * 
     * @date 2019年5月16日 上午10:57:24
     * @param base
     * @return
     */
    @RequestMapping(value = "/accessorys/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<AccessoryInfo> addAccessory(@RequestBody AccessoryInfo base);

    @RequestMapping(value = "/accessorys/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<AccessoryInfo>> accessoryPage(@RequestBody PageParam<AccessoryInfo> base);

    @RequestMapping(value = "/accessorys/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<AccessoryInfo> editAccessory(@RequestBody AccessoryInfo base);

    @RequestMapping(value = "/accessorys/{markId}", method = RequestMethod.GET)
    Result<AccessoryInfo> getAccessoryInfo(@PathVariable("markId") String markId);

    /**
     * 采购管理
     * 
     * @date 2019年5月16日 下午4:50:42
     * @return
     */
    @RequestMapping(value = "/purchases/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<PurchaseFood>> purchasePage(@RequestBody PageParam<PurchaseInfo> base);

    @RequestMapping(value = "/purchases/history/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<PurchaseHistoryVo>> purchaseHistoryPage(
            @RequestBody PageParam<PurchaseHistory> base);

    @RequestMapping(value = "/purchases/appoin", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> appoinStaff(@RequestParam("markId") String markId,
            @RequestParam("userId") String userId);

    @RequestMapping(value = "/purchases/revoke", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> revokeStaff(@RequestParam("markId") String markId);

    @RequestMapping(value = "/purchases/buy", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> buyFood(@RequestBody PurchaseInfo base);

    @RequestMapping(value = "/purchases/create", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> createPurchaseOrder();

    @RequestMapping(value = "/purchases/clear", method = RequestMethod.DELETE)
    Result<?> clearPurchaseOrder(@RequestParam("buyTime") String buyTime);

    @RequestMapping(value = "/purchases/reflash", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> reflashPurchaseOrder();

    @RequestMapping(value = "/goods/column/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByColumn(@RequestBody PageParam<GoodsInfo> base);

    @RequestMapping(value = "/goods/label/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByLabel(@RequestBody PageParam<GoodsInfo> base);

    @RequestMapping(value = "/goods/icon/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageByIcon(@RequestBody PageParam<GoodsInfo> base);

    @RequestMapping(value = "/goods/special/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<GoodsVo>> getPageBySpecial(@RequestBody PageParam<GoodsInfo> base);

    // 套餐添加栏目图标等
    @RequestMapping(value = "/columns/meal/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<ColumnMealVo>> getColumnMealPage(@RequestBody PageParam<ColumnGoods> base);

    @RequestMapping(value = "/meals/column/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByColumn(@RequestBody PageParam<MealInfo> base);

    @RequestMapping(value = "/labels/meal/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<LabelMealVo>> getLabelMealPage(@RequestBody PageParam<LabelGoods> base);

    @RequestMapping(value = "/meals/label/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByLabel(@RequestBody PageParam<MealInfo> base);

    @RequestMapping(value = "/meals/special/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageBySpecial(@RequestBody PageParam<MealInfo> base);

    @RequestMapping(value = "/meals/icon/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<MealInfo>> getMealPageByIcon(@RequestBody PageParam<MealInfo> base);

    /**
     * 厨师
     */
    @RequestMapping(value = "/cooks/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<CookCertified> modifyCertified(@RequestBody CookCertified cookCertified);

    @RequestMapping(value = "/cooks/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<CookCertified> addCertified(@RequestBody CookCertified cookCertified);

    @RequestMapping(value = "/cooks/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<CookCertified>> pageCook(@RequestBody PageParam<CookCertified> cookPage);

    @RequestMapping(value = "/purchases/list", method = RequestMethod.GET)
    Result<List<PurchaseFood>> getPurchaseListByStatus(@RequestParam("userId") String userId,
            @RequestParam("status") Integer status);
}
