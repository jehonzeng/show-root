package com.szhengzhu.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

@FeignClient("show-goods")
public interface ShowGoodsClient {

    @RequestMapping(value = "/labels/goods/list", method = RequestMethod.GET)
    Result<List<Label>> listLabelGoods();
    
    @RequestMapping(value = "/goods/fore/index/recommend", method = RequestMethod.GET)
    Result<List<GoodsBase>> listRecommend(@RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/goods/fore/detail", method = RequestMethod.GET)
    Result<GoodsInfoVo> getGoodsDetail(@RequestParam("goodsId") String goodsId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/cooks/sale/rank", method = RequestMethod.GET)
    Result<List<Cooker>> listCookerRank(@RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/cooks/sale/rank/goods/page", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<PageGrid<Cooker>> pageCookerRank(@RequestBody PageParam<String> cookerPage);
    
    @RequestMapping(value = "/cooks/detail/{cookerId}", method = RequestMethod.GET)
    Result<?> getCookerDetail(@RequestParam("cookerId") String cookerId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/cooks/follow/or", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<?> cookFollowOr(@RequestBody CookFollow cookFollow);
    
    @RequestMapping(value = "/cooks/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addCooker(@RequestBody CookCertified cooker);
    
    @RequestMapping(value = "/goodsVouchers/fore/detail", method = RequestMethod.GET)
    Result<GoodsInfoVo> getVoucherDetail(@RequestParam("voucherId") String voucherId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/meals/fore/detail", method = RequestMethod.GET)
    Result<GoodsInfoVo> getMealDetail(@RequestParam("mealId") String mealId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/meals/stock/info", method = RequestMethod.GET)
    Result<StockBase> getMealStock(@RequestParam("mealId") String mealId, @RequestParam(value = "addressId", required = false) String addressId);
    
    @RequestMapping(value = "/carts/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addCart(@RequestBody ShoppingCart cart);
    
    @RequestMapping(value = "/carts/modify", method = RequestMethod.PATCH, consumes = Contacts.CONSUMES)
    Result<ShoppingCart> modifyCart(@RequestBody ShoppingCart cart);
    
    @RequestMapping(value = "/carts/list", method = RequestMethod.GET)
    Result<List<ShoppingCart>> listCart(@RequestParam("userId") String userId);
    
    @RequestMapping(value = "/carts/addition", method = RequestMethod.GET)
    Result<Map<String, Object>> getCartAddition();
    
    @RequestMapping(value = "/carts/calc", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<CalcData> calcTotal(@RequestBody OrderModel orderModel);
    
    @RequestMapping(value = "/specifications/list", method = RequestMethod.GET)
    Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") String goodsId);
    
    @RequestMapping(value = "/stocks/info", method = RequestMethod.GET)
    Result<StockBase> getGoodsStcokInfo(@RequestParam("goodsId") String goodsId,
            @RequestParam("specIds") String specIds, @RequestParam(value = "addressId", required = false) String addressId);
    
    @RequestMapping(value = "/judges/fore/list", method = RequestMethod.GET)
    Result<List<JudgeBase>> listGoodsJudge(@RequestParam("goodsId") String goodsId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/judges/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addGoodsJudge(@RequestBody GoodsJudge judge);
    
    @RequestMapping(value = "/comments/fore/list", method = RequestMethod.GET)
    Result<List<JudgeBase>> listMealJudge(@RequestParam("mealId") String mealId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/comments/add", method = RequestMethod.POST, consumes = Contacts.CONSUMES)
    Result<?> addMealJudge(@RequestBody MealJudge judge);
    
    @RequestMapping(value = "/goodsVouchers/fore/judge", method = RequestMethod.GET)
    Result<List<JudgeBase>> listVoucherJudge(@RequestParam("voucherId") String voucherId, @RequestParam(value = "userId", required = false) String userId);
    
    @RequestMapping(value = "/goodsVouchers/stock/info", method = RequestMethod.GET)
    Result<StockBase> getVoucherStock(@RequestParam("voucherId") String voucherId);
}
