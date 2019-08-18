package com.szhengzhu.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.goods.GoodsContent;
import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.FileUtils;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "商品管理：GoodsController" })
@RestController
@RequestMapping(value = "/v1/goods")
public class GoodsController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "编辑商品信息", notes = "编辑商品信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<GoodsInfo> modifyGoods(HttpSession session, @RequestBody GoodsInfo goodsInfo) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        goodsInfo.setModifier(userId);
        return showGoodsClient.modifyGoods(goodsInfo);
    }

    @ApiOperation(value = "修改商品上下架状态", notes = "修改商品上下架状态")
    @RequestMapping(value = "/editStatus", method = RequestMethod.PATCH)
    public Result<GoodsInfo> modifyGoodsStatus(HttpSession session, @RequestBody GoodsInfo base) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        base.setModifier(userId);
        return showGoodsClient.editGoodsStatus(base);
    }

    @ApiOperation(value = "添加商品信息", notes = "添加商品信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<GoodsInfo> addGoods(HttpSession session, @RequestBody GoodsInfo goodsInfo) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        goodsInfo.setCreator(userId);
        return showGoodsClient.addGoods(goodsInfo);
    }

    @ApiOperation(value = "商品基础信息分页", notes = "商品基础信息分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> goodsPage(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.goodsPage(base);
    }

    @ApiOperation(value = "根据id商品基础信息", notes = "根据id商品基础信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<GoodsInfo> goodsInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.goodsInfo(markId);
    }

    @ApiOperation(value = "获取商品与规格组合价格分页列表", notes = "获取商品与规格组合价格分页列表")
    @RequestMapping(value = "/specification/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsSpecification>> pageGoodsSpec(
            @RequestBody PageParam<GoodsSpecification> base) {
        return showGoodsClient.pageGoodsSpec(base);
    }

    @ApiOperation(value = "添加商品与规格组合价格", notes = "添加商品与规格组合价格")
    @RequestMapping(value = "/specification", method = RequestMethod.POST)
    public Result<?> addGoodsSpec(@RequestBody GoodsSpecification base) {
        return showGoodsClient.addGoosSpec(base);
    }

    @ApiOperation(value = "修改商品与规格组合价格", notes = "修改商品与规格组合价格")
    @RequestMapping(value = "/specification", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsSpec(@RequestBody GoodsSpecification base) {
        return showGoodsClient.modifyGoodsSpec(base);
    }

    @ApiOperation(value = "获取商品下拉列表", notes = "获取商品下拉列表")
    @RequestMapping(value = "/combobx", method = RequestMethod.GET)
    public Result<List<Combobox>> listGoodsCombobox() {
        return showGoodsClient.listGoodsCombobox();
    }

    @ApiOperation(value = "批量添加类型与规格关联关系", notes = "批量添加类型与规格关联关系")
    @RequestMapping(value = "/type/specification", method = RequestMethod.POST)
    public Result<?> addTypeSpec(@RequestParam("specIds") String[] specIds,
            @RequestParam("typeId") String typeId) {
        return showGoodsClient.addTypeSpec(specIds, typeId);
    }

    @ApiOperation(value = "删除类型与规格关联关系", notes = "删除类型与规格关联关系")
    @RequestMapping(value = "/type/specification", method = RequestMethod.DELETE)
    public Result<?> removeTypeSpec(@RequestParam("typeId") String typeId,
            @RequestParam("specId") String specId) {
        return showGoodsClient.removeTypeSpec(typeId, specId);
    }

    @ApiOperation(value = "修改类型与规格关联关系", notes = "修改类型与规格关联关系")
    @RequestMapping(value = "/type/specification", method = RequestMethod.PATCH)
    public Result<?> modifyTypeSpec(@RequestBody TypeSpec typeSpec) {
        return showGoodsClient.modifyTypeSpec(typeSpec);
    }

    @ApiOperation(value = "编辑保存商品文字图片详情", notes = "编辑保存商品文字图片详情")
    @RequestMapping(value = "/content", method = RequestMethod.PATCH)
    public Result<GoodsContent> modifyContent(@RequestBody GoodsContent goodsContent) {
        return showGoodsClient.modifyContent(goodsContent);
    }

    @ApiOperation(value = "上传并显示商品详情图片", notes = "上传商品详情图片")
    @RequestMapping(value = "/content", method = RequestMethod.POST)
    public Result<String> contentImage(
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setMarkId(IdGenerator.getInstance().nexId());
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if (FileUtils.isImgSuffix(fileType)) {
                String savePath = TimeUtils.textDate() + File.separator + imageInfo.getMarkId()
                        + fileType;
                FileUtils.uploadImg(file, Contacts.BASE_IMG_PATH + File.separator + savePath);
                imageInfo.setImagePath(savePath);
                imageInfo.setFileType(fileType.substring(fileType.lastIndexOf(".") + 1));
            } else {
                return new Result<>(StatusCode._5005);
            }
        }
        showBaseClient.addImgInfo(imageInfo);
        return new Result<String>(Contacts.IMAGE_SERVER + "/shows/" + imageInfo.getMarkId());
    }

    @ApiOperation(value = "根据商品id显示商品图片文字详情", notes = "根据商品id显示商品图片文字详情")
    @RequestMapping(value = "/content/{goodsId}", method = RequestMethod.GET)
    public Result<GoodsContent> showContent(@PathVariable("goodsId") String goodsId) {
        return showGoodsClient.showContent(goodsId);
    }

    @ApiOperation(value = "添加商品图片信息", notes = "保存商品图片信息")
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public Result<GoodsImage> addGoodsImage(@RequestBody GoodsImage base) {
        return showGoodsClient.addGoodsImage(base);
    }

    @ApiOperation(value = "修改商品图片信息", notes = "修改商品图片信息")
    @RequestMapping(value = "/image", method = RequestMethod.PATCH)
    public Result<GoodsImage> modifyGoodsImage(@RequestBody GoodsImage base) {
        return showGoodsClient.modifyGoodsImage(base);
    }

    @ApiOperation(value = "删除选定商品图片信息", notes = "删除选定商品图片信息")
    @RequestMapping(value = "/image/{markId}", method = RequestMethod.DELETE)
    public Result<GoodsImage> deleteGoodsImage(@PathVariable("markId") String markId) {
        GoodsImage image = showGoodsClient.getImageInfo(markId);
        if (image == null)
            return new Result<>(StatusCode._4008);
        showBaseClient.deleteImage(image.getImagePath());
        return showGoodsClient.deleteGoodsImage(markId);
    }

    @ApiOperation(value = "获取不同类型的商品图片列表", notes = "获取不同类型的商品图片列表")
    @RequestMapping(value = "/image/{goodsId}", method = RequestMethod.GET)
    public Result<Map<String, Object>> showGoodsImage(@PathVariable("goodsId") String goodsId,
            @RequestParam(defaultValue = "0") Integer serverType) {
        return showGoodsClient.showGoodsImage(goodsId, serverType);
    }

    @ApiOperation(value = "添加商品规格类型", notes = "添加商品规格类型")
    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public Result<GoodsType> addGoodsType(@RequestBody GoodsType base) {
        return showGoodsClient.addGoodsType(base);
    }

    @ApiOperation(value = "修改商品规格类型", notes = "编辑商品规格类型")
    @RequestMapping(value = "/type", method = RequestMethod.PATCH)
    public Result<GoodsType> editGoodsType(@RequestBody GoodsType base) {
        return showGoodsClient.modifyGoodsType(base);
    }

    @ApiOperation(value = "获取商品规格类型分页", notes = "获取商品规格类型分页")
    @RequestMapping(value = "/type/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsType>> typePage(@RequestBody PageParam<GoodsType> base) {
        return showGoodsClient.getTypePage(base);
    }

    @ApiOperation(value = "获取商品规格类型下拉列表", notes = "获取商品规格类型下拉列表")
    @RequestMapping(value = "/type/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listTypeCombobox() {
        return showGoodsClient.listTypeCombobox();
    }

    @ApiOperation(value = "修改商品评论信息", notes = "修改商品评论信息")
    @RequestMapping(value = "/judge", method = RequestMethod.PATCH)
    public Result<GoodsJudge> modifyGoodsJudes(@RequestBody GoodsJudge base) {
        return showGoodsClient.modifyGoodsJudes(base);
    }

    @ApiOperation(value = "获取商品评论分页", notes = "根据不同条件获取商品评论分页")
    @RequestMapping(value = "/judge/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsJudgeVo>> judgePage(@RequestBody PageParam<GoodsJudge> base) {
        return showGoodsClient.judgePage(base);
    }

    @ApiOperation(value = "获取未加入栏目的商品下拉列表", notes = "获取没有加入栏目的商品列表")
    @RequestMapping(value = "/listNotColumn", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotColumn() {
        return showGoodsClient.getListNotColumn();
    }

    @ApiOperation(value = "获取未加入某一标签的商品下拉列表", notes = "获取未加入某一标签的商品列表")
    @RequestMapping(value = "/listNotLabel", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotLabel(@RequestParam("labelId") String labelId) {
        return showGoodsClient.getListNotLabel(labelId);
    }

    @ApiOperation(value = "获取没有设置图标的商品下拉列表", notes = "获取没有设置图标的商品列表")
    @RequestMapping(value = "/listNotIcon", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotIcon() {
        return showGoodsClient.getListNotIcon();
    }

    @ApiOperation(value = "获取没有设置特价的商品下拉列表", notes = "获取没有设置特价的商品列表")
    @RequestMapping(value = "/listNotSpecial", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotSpecial() {
        return showGoodsClient.getListNotSpecial();
    }

}
