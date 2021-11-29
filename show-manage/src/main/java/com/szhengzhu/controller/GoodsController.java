package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.goods.*;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsJudgeVo;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Validated
@Api(tags = {"商品管理：GoodsController"})
@RestController
@RequestMapping(value = "/v1/goods")
public class GoodsController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private FtpServer ftpServer;

    @ApiOperation(value = "编辑商品信息", notes = "编辑商品信息")
    @PatchMapping(value = "")
    public Result<GoodsInfo> modifyGoods(HttpSession session, @RequestBody @Validated GoodsInfo goodsInfo) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        goodsInfo.setModifier(userId);
        return showGoodsClient.modifyGoods(goodsInfo);
    }

    @ApiOperation(value = "修改商品上下架状态", notes = "修改商品上下架状态")
    @PatchMapping(value = "/editStatus")
    public Result<GoodsInfo> modifyGoodsStatus(HttpSession session, @RequestBody GoodsInfo base) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        base.setModifier(userId);
        return showGoodsClient.editGoodsStatus(base);
    }

    @ApiOperation(value = "添加商品信息", notes = "添加商品信息")
    @PostMapping(value = "")
    public Result<GoodsInfo> addGoods(HttpSession session, @RequestBody @Validated GoodsInfo goodsInfo) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        goodsInfo.setCreator(userId);
        return showGoodsClient.addGoods(goodsInfo);
    }

    @ApiOperation(value = "商品基础信息分页", notes = "商品基础信息分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<GoodsVo>> goodsPage(@RequestBody PageParam<GoodsInfo> base) {
        return showGoodsClient.goodsPage(base);
    }

    @ApiOperation(value = "根据id商品基础信息", notes = "根据id商品基础信息")
    @GetMapping(value = "/{markId}")
    public Result<GoodsInfo> goodsInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.goodsInfo(markId);
    }

    @ApiOperation(value = "获取商品与规格组合价格分页列表", notes = "获取商品与规格组合价格分页列表")
    @PostMapping(value = "/specification/page")
    public Result<PageGrid<GoodsSpecification>> pageGoodsSpec(
            @RequestBody PageParam<GoodsSpecification> base) {
        return showGoodsClient.pageGoodsSpec(base);
    }

    @ApiOperation(value = "添加商品与规格组合价格", notes = "添加商品与规格组合价格")
    @PostMapping(value = "/specification")
    public Result addGoodsSpec(@RequestBody @Validated GoodsSpecification base) {
        return showGoodsClient.addGoodsSpec(base);
    }

    @ApiOperation(value = "修改商品与规格组合价格", notes = "修改商品与规格组合价格")
    @PatchMapping(value = "/specification")
    public Result modifyGoodsSpec(@RequestBody @Validated GoodsSpecification base) {
        return showGoodsClient.modifyGoodsSpec(base);
    }

    @ApiOperation(value = "获取商品下拉列表", notes = "获取商品下拉列表")
    @GetMapping(value = "/combobx")
    public Result<List<Combobox>> listGoodsCombobox() {
        return showGoodsClient.listGoodsCombobox();
    }

    @ApiOperation(value = "批量添加类型与规格关联关系", notes = "批量添加类型与规格关联关系")
    @PostMapping(value = "/type/specification")
    public Result addTypeSpec(@RequestParam("specIds") @NotEmpty String[] specIds,
                                 @RequestParam("typeId") @NotBlank String typeId) {
        return showGoodsClient.addTypeSpec(specIds, typeId);
    }

    @ApiOperation(value = "删除类型与规格关联关系", notes = "删除类型与规格关联关系")
    @DeleteMapping(value = "/type/specification")
    public Result removeTypeSpec(@RequestParam("typeId") @NotBlank String typeId,
                                    @RequestParam("specId") @NotBlank String specId) {
        return showGoodsClient.removeTypeSpec(typeId, specId);
    }

    @ApiOperation(value = "修改类型与规格关联关系", notes = "修改类型与规格关联关系")
    @PatchMapping(value = "/type/specification")
    public Result modifyTypeSpec(@RequestBody @Validated TypeSpec typeSpec) {
        return showGoodsClient.modifyTypeSpec(typeSpec);
    }

    @ApiOperation(value = "编辑保存商品文字图片详情", notes = "编辑保存商品文字图片详情")
    @PatchMapping(value = "/content")
    public Result<GoodsContent> modifyContent(@RequestBody @Validated GoodsContent goodsContent) {
        return showGoodsClient.modifyContent(goodsContent);
    }

    @ApiOperation(value = "上传并显示商品详情图片", notes = "上传商品详情图片")
    @PostMapping(value = "/content")
    public Result<String> contentImage(
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ShowAssert.checkTrue((ObjectUtil.isNull(file) || file.isEmpty()), StatusCode._4009);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        ImageInfo imageInfo = ImageInfo.builder().markId(snowflake.nextIdStr()).build();
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        ShowAssert.checkTrue(!FileUtils.isImgSuffix(fileType), StatusCode._5005);
        String savePath = "/" + DateUtil.today() + "/";
        String newFileName = imageInfo.getMarkId() + fileType;
        boolean flag = ftpServer.upload(savePath, newFileName, file.getInputStream());
        ShowAssert.checkTrue(!flag, StatusCode._4008);
        imageInfo.setImagePath(savePath);
        imageInfo.setFileType(fileType.substring(fileType.lastIndexOf(".") + 1));
        showBaseClient.addImgInfo(imageInfo);
        return new Result<>(Contacts.IMAGE_SERVER + "/shows/" + imageInfo.getMarkId());
    }

    @ApiOperation(value = "根据商品id显示商品图片文字详情", notes = "根据商品id显示商品图片文字详情")
    @GetMapping(value = "/content/{goodsId}")
    public Result<GoodsContent> showContent(@PathVariable("goodsId") @NotBlank String goodsId) {
        return showGoodsClient.showContent(goodsId);
    }

    @ApiOperation(value = "添加商品图片信息", notes = "保存商品图片信息")
    @PostMapping(value = "/image")
    public Result<GoodsImage> addGoodsImage(@RequestBody @Validated GoodsImage base) {
        return showGoodsClient.addGoodsImage(base);
    }

    @ApiOperation(value = "修改商品图片信息", notes = "修改商品图片信息")
    @PatchMapping(value = "/image")
    public Result<GoodsImage> modifyGoodsImage(@RequestBody @Validated GoodsImage base) {
        return showGoodsClient.modifyGoodsImage(base);
    }

    @ApiOperation(value = "删除选定商品图片信息", notes = "删除选定商品图片信息")
    @DeleteMapping(value = "/image/{markId}")
    public Result deleteGoodsImage(@PathVariable("markId") @NotBlank String markId) {
        Result<GoodsImage> result = showGoodsClient.getImageInfo(markId);
        ShowAssert.checkResult(result);
        showBaseClient.deleteImage(result.getData().getImagePath());
        return showGoodsClient.deleteGoodsImage(markId);
    }

    @ApiOperation(value = "获取不同类型的商品图片列表", notes = "获取不同类型的商品图片列表")
    @GetMapping(value = "/image/{goodsId}")
    public Result<Map<String, Object>> showGoodsImage(@PathVariable("goodsId") @NotBlank String goodsId,
                                                      @RequestParam(defaultValue = "0") @NotNull Integer serverType) {
        return showGoodsClient.showGoodsImage(goodsId, serverType);
    }

    @ApiOperation(value = "添加商品规格类型", notes = "添加商品规格类型")
    @PostMapping(value = "/type")
    public Result<GoodsType> addGoodsType(@RequestBody @NotBlank GoodsType base) {
        return showGoodsClient.addGoodsType(base);
    }

    @ApiOperation(value = "修改商品规格类型", notes = "编辑商品规格类型")
    @PatchMapping(value = "/type")
    public Result<GoodsType> editGoodsType(@RequestBody @NotBlank GoodsType base) {
        return showGoodsClient.modifyGoodsType(base);
    }

    @ApiOperation(value = "获取商品规格类型分页", notes = "获取商品规格类型分页")
    @PostMapping(value = "/type/page")
    public Result<PageGrid<GoodsType>> typePage(@RequestBody PageParam<GoodsType> base) {
        return showGoodsClient.getTypePage(base);
    }

    @ApiOperation(value = "获取商品规格类型下拉列表", notes = "获取商品规格类型下拉列表")
    @GetMapping(value = "/type/combobox")
    public Result<List<Combobox>> listTypeCombobox() {
        return showGoodsClient.listTypeCombobox();
    }

    @ApiOperation(value = "修改商品评论信息", notes = "修改商品评论信息")
    @PatchMapping(value = "/judge")
    public Result<GoodsJudge> modifyGoodsJudes(@RequestBody @NotBlank GoodsJudge base) {
        return showGoodsClient.modifyGoodsJudes(base);
    }

    @ApiOperation(value = "获取商品评论分页", notes = "根据不同条件获取商品评论分页")
    @PostMapping(value = "/judge/page")
    public Result<PageGrid<GoodsJudgeVo>> judgePage(@RequestBody PageParam<GoodsJudge> base) {
        return showGoodsClient.judgePage(base);
    }

    @ApiOperation(value = "获取未加入栏目的商品下拉列表", notes = "获取没有加入栏目的商品列表")
    @GetMapping(value = "/listNotColumn")
    public Result<List<Combobox>> listNotColumn() {
        return showGoodsClient.getListNotColumn();
    }

    @ApiOperation(value = "获取未加入某一标签的商品下拉列表", notes = "获取未加入某一标签的商品列表")
    @GetMapping(value = "/listNotLabel")
    public Result<List<Combobox>> listNotLabel(@RequestParam("labelId") @NotBlank String labelId) {
        return showGoodsClient.getListNotLabel(labelId);
    }

    @ApiOperation(value = "获取没有设置图标的商品下拉列表", notes = "获取没有设置图标的商品列表")
    @GetMapping(value = "/listNotIcon")
    public Result<List<Combobox>> listNotIcon() {
        return showGoodsClient.getListNotIcon();
    }

    @ApiOperation(value = "获取没有设置特价的商品下拉列表", notes = "获取没有设置特价的商品列表")
    @GetMapping(value = "/listNotSpecial")
    public Result<List<Combobox>> listNotSpecial() {
        return showGoodsClient.getListNotSpecial();
    }

}
