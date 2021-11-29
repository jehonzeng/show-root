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
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.vo.MealVo;
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
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"套餐管理：MealController"})
@RestController
@RequestMapping(value = "/v1/meals")
public class MealController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private FtpServer ftpServer;

    @ApiOperation(value = "添加套餐信息", notes = "添加套餐信息")
    @PostMapping(value = "")
    public Result<?> save(@RequestBody @Validated MealInfo base) {
        return showGoodsClient.saveMeal(base);
    }

    @ApiOperation(value = "修改套餐信息", notes = "修改套餐信息")
    @PatchMapping(value = "")
    public Result<MealInfo> edit(@RequestBody @Validated MealInfo base) {
        return showGoodsClient.editMeal(base);
    }

    @ApiOperation(value = "获取套餐下拉列表", notes = "获取套餐下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listMealCombobox();
    }

    @ApiOperation(value = "添加套餐单品信息", notes = "添加套餐单品信息")
    @PostMapping(value = "/item")
    public Result<MealItem> saveItem(@RequestBody @Validated MealItem base) {
        return showGoodsClient.saveMealItem(base);
    }

    @ApiOperation(value = "修改套餐单品信息", notes = "修改套餐单品信息")
    @PatchMapping(value = "/item")
    public Result<?> editItem(@RequestBody @Validated MealItem base) {
        return showGoodsClient.editMealItem(base);
    }

    @ApiOperation(value = "套餐单品列表信息", notes = "套餐单品列表信息")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<MealVo>> itemPage(@RequestBody PageParam<MealItem> base) {
        return showGoodsClient.getMealItemPage(base);
    }

    @ApiOperation(value = "套餐列表信息", notes = "套餐列表信息")
    @PostMapping(value = "/page")
    public Result<PageGrid<MealInfo>> page(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPage(base);
    }

    @ApiOperation(value = "根据id获取套餐信息", notes = "根据id获取套餐信息")
    @GetMapping(value = "/{markId}")
    public Result<MealInfo> getMealInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getMealById(markId);
    }

    @ApiOperation(value = "根据id获取套餐单品信息", notes = "根据套餐id获取套餐单品信息")
    @GetMapping(value = "/item/{markId}")
    public Result<MealItem> getMealItemById(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getMealItemById(markId);
    }

    @ApiOperation(value = "编辑保存套餐图文详情内容", notes = "编辑保存套餐图文详情内容")
    @PatchMapping(value = "/content")
    public Result<MealContent> editMealContent(@RequestBody @NotBlank MealContent base) {
        return showGoodsClient.editMealContent(base);
    }

    @ApiOperation(value = "根据套餐id获取图文详情内容", notes = "根据套餐id获取图文详情内容")
    @GetMapping(value = "/content/{mealId}")
    public Result<MealContent> getMealContent(@PathVariable("mealId") @NotBlank String mealId) {
        return showGoodsClient.getMealContent(mealId);
    }

    @ApiOperation(value = "上传并显示套餐商品详情图片", notes = "上传并显示套餐商品详情图片")
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

    @ApiOperation(value = "添加套餐图片信息", notes = "添加套餐图片信息")
    @PostMapping(value = "/image")
    public Result<MealImage> addMealImage(@RequestBody @Validated MealImage base) {
        return showGoodsClient.addMealImage(base);
    }

    @ApiOperation(value = "修改套餐图片信息", notes = "修改套餐图片信息")
    @PatchMapping(value = "/image")
    public Result<MealImage> modifyMealImage(@RequestBody @Validated MealImage base) {
        return showGoodsClient.modifyMealImage(base);
    }

    @ApiOperation(value = "删除显示的图片", notes = "删除显示的图片")
    @DeleteMapping(value = "/image/{markId}")
    public Result<MealImage> deleteMealImage(@PathVariable("markId") @NotBlank String markId) {
        Result<MealImage> result = showGoodsClient.getMealImageInfo(markId);
        ShowAssert.checkResult(result);
        showBaseClient.deleteImage(result.getData().getImagePath());
        return showGoodsClient.deleteMealImage(markId);
    }

    @ApiOperation(value = "获取不同类型的套餐图片列表", notes = "获取不同类型的套餐图片列表")
    @GetMapping(value = "/image/{mealId}")
    public Result<Map<String, Object>> getMealImages(@PathVariable("mealId") @NotBlank String mealId,
                                                     @RequestParam("serverType") Integer serverType) {
        return showGoodsClient.getMealImages(mealId, serverType);
    }

    @ApiOperation(value = "修改套餐评论信息", notes = "修改套餐评论信息")
    @PatchMapping(value = "/comment")
    public Result<MealJudge> modifyMealJudge(@RequestBody @Validated MealJudge base) {
        return showGoodsClient.modifyMealJudge(base);
    }

    @ApiOperation(value = "获取套餐评论信息列表", notes = "获取套餐评论信息列表")
    @PostMapping(value = "/comment/page")
    public Result<PageGrid<MealJudgeVo>> MealJudgePage(@RequestBody PageParam<MealJudge> base) {
        return showGoodsClient.mealJudgePage(base);
    }
}
