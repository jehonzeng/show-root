package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.context.ProductContext;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.handler.AbstractProduct;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Validated
@Api(tags = {"商品专题：GoodController"})
@RestController
@RequestMapping("/goods")
public class GoodController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ProductContext context;

    @Resource
    private OrderContext orderContext;

    @ApiOperation(value = "获取标签分类商品列表", notes = "获取标签分类商品列表")
    @GetMapping(value = "/label/list")
    public Result<List<Label>> listLabelGoods() {
        return showGoodsClient.listLabelGoods();
    }

    @ApiOperation(value = "获取某标签分类的商品列表", notes = "获取某标签分类的商品列表")
    @GetMapping(value = "/label/{labelId}/product")
    public Result<List<GoodsBase>> listLabelGoods(@PathVariable("labelId") @NotBlank String labelId) {
        return showGoodsClient.listLabelGoods(labelId);
    }

    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    @GetMapping(value = "/{productId}")
    public Result<GoodsDetail> getGoodsDetail(HttpServletRequest request, @PathVariable("productId") @NotBlank String productId,
                                              @RequestParam("productType") @NotNull Integer productType) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        AbstractProduct handler = context.getInstance(productType.toString());
        return handler.getProductDetail(productId, userToken.getUserId());
    }

    @ApiOperation(value = "根据goodsId获取规格属性选项列表", notes = "根据goodsId获取规格属性选项列表")
    @GetMapping(value = "/list")
    public Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.listSpecification(goodsId);
    }

    @ApiOperation(value = "商品详情页获取规格商品是否配送及当前库存", notes = "商品详情页获取规格商品是否配送及当前库存")
    @PostMapping(value = "/spec/stock")
    public Result<StockBase> getSpecStock(@RequestBody @Validated StockParam stockParam) {
        AbstractProduct handler = context.getInstance(stockParam.getProductType().toString());
        return handler.getProductStock(stockParam);
    }

    @ApiOperation(value = "商城获取商品评论列表", notes = "商城获取商品评论列表")
    @GetMapping(value = "/judge/list/{productId}")
    public Result<List<JudgeBase>> listGoodJudge(HttpServletRequest request,
                                                 @PathVariable("productId") @NotBlank String productId, @RequestParam("productType") @NotNull Integer productType) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        AbstractProduct handler = context.getInstance(productType.toString());
        return handler.listGoodsJudge(productId, userToken.getUserId());
    }

    @ApiOperation(value = "添加订单商品评价", notes = "添加订单商品评价")
    @PostMapping(value = "/judge")
    public Result addJudge(HttpServletRequest request, @RequestBody @NotEmpty List<Judge> judges) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        String userId = userInfo.getMarkId();
        String orderNo = judges.get(0).getOrderNo();
        String orderType = Character.toString(orderNo.charAt(0));
        AbstractOrder orderHandler = orderContext.getInstance(orderType);
        String orderId = orderHandler.orderJudge(orderNo, userId);
        for (Judge judge : judges) {
            AbstractProduct handler = context.getInstance(judge.getProductType().toString());
            handler.addProductJudge(judge, orderId, userId, userInfo.getNickName());
        }
        return new Result<>();
    }

    @ApiOperation(value = "品牌加盟添加信息", notes = "品牌加盟添加信息")
    @PostMapping(value = "/brand")
    public Result<BrandInfo> addBrand(@RequestBody BrandInfo base) {
        return showGoodsClient.addBrand(base);
    }
}
