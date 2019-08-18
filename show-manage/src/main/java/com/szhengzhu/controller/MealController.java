package com.szhengzhu.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.goods.MealContent;
import com.szhengzhu.bean.goods.MealImage;
import com.szhengzhu.bean.goods.MealInfo;
import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.MealJudgeVo;
import com.szhengzhu.bean.vo.MealVo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
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

@Api(tags = { "套餐管理：MealController" })
@RestController
@RequestMapping(value = "/v1/meals")
public class MealController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加套餐信息", notes = "添加套餐信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> save(@RequestBody MealInfo base) {
        return showGoodsClient.saveMeal(base);
    }

    @ApiOperation(value = "修改套餐信息", notes = "修改套餐信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<MealInfo> edit(@RequestBody MealInfo base) {
        return showGoodsClient.editMeal(base);
    }

    @ApiOperation(value = "获取套餐下拉列表", notes = "获取套餐下拉列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listMealCombobox();
    }

    @ApiOperation(value = "添加套餐单品信息", notes = "添加套餐单品信息")
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public Result<MealItem> saveItem(@RequestBody MealItem base) {
        return showGoodsClient.saveMealItem(base);
    }

    @ApiOperation(value = "修改套餐单品信息", notes = "修改套餐单品信息")
    @RequestMapping(value = "/item", method = RequestMethod.PATCH)
    public Result<?> editItem(@RequestBody MealItem base) {
        return showGoodsClient.editMealItem(base);
    }

    @ApiOperation(value = "套餐单品列表信息", notes = "套餐单品列表信息")
    @RequestMapping(value = "/item/page", method = RequestMethod.POST)
    public Result<PageGrid<MealVo>> itemPage(@RequestBody PageParam<MealItem> base) {
        return showGoodsClient.getMealItemPage(base);
    }

    @ApiOperation(value = "套餐列表信息", notes = "套餐列表信息")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<MealInfo>> page(@RequestBody PageParam<MealInfo> base) {
        return showGoodsClient.getMealPage(base);
    }

    @ApiOperation(value = "根据id获取套餐信息", notes = "根据id获取套餐信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<MealInfo> getMealInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.getMealById(markId);
    }

    @ApiOperation(value = "根据id获取套餐单品信息", notes = "根据套餐id获取套餐单品信息")
    @RequestMapping(value = "/item/{markId}", method = RequestMethod.GET)
    public Result<MealItem> getMealItemById(@PathVariable("markId") String markId) {
        return showGoodsClient.getMealItemById(markId);
    }

    @ApiOperation(value = "编辑保存套餐图文详情内容", notes = "编辑保存套餐图文详情内容")
    @RequestMapping(value = "/content", method = RequestMethod.PATCH)
    public Result<MealContent> editMealContent(@RequestBody MealContent base) {
        return showGoodsClient.editMealContent(base);
    }

    @ApiOperation(value = "根据套餐id获取图文详情内容", notes = "根据套餐id获取图文详情内容")
    @RequestMapping(value = "/content/{mealId}", method = RequestMethod.GET)
    public Result<MealContent> getMealContent(@PathVariable("mealId") String mealId) {
        return showGoodsClient.getMealContent(mealId);
    }

    @ApiOperation(value = "上传并显示套餐商品详情图片", notes = "上传并显示套餐商品详情图片")
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

    @ApiOperation(value = "添加套餐图片信息", notes = "添加套餐图片信息")
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public Result<MealImage> addMealImage(@RequestBody MealImage base) {
        return showGoodsClient.addMealImage(base);
    }

    @ApiOperation(value = "修改套餐图片信息", notes = "修改套餐图片信息")
    @RequestMapping(value = "/image", method = RequestMethod.PATCH)
    public Result<MealImage> modifyMealImage(@RequestBody MealImage base) {
        return showGoodsClient.modifyMealImage(base);
    }

    @ApiOperation(value = "删除显示的图片", notes = "删除显示的图片")
    @RequestMapping(value = "/image/{markId}", method = RequestMethod.DELETE)
    public Result<MealImage> deleteMealImage(@PathVariable("markId") String markId) {
        MealImage image = showGoodsClient.getMealImageInfo(markId);
        if (image == null)
            return new Result<>(StatusCode._4008);
        showBaseClient.deleteImage(image.getImagePath());
        return showGoodsClient.deleteMealImage(markId);
    }

    @ApiOperation(value = "获取不同类型的套餐图片列表", notes = "获取不同类型的套餐图片列表")
    @RequestMapping(value = "/image/{mealId}", method = RequestMethod.GET)
    public Result<Map<String, Object>> getMealImages(@PathVariable("mealId") String mealId,
            @RequestParam("serverType") Integer serverType) {
        return showGoodsClient.getMealImages(mealId, serverType);
    }

    @ApiOperation(value = "修改套餐评论信息", notes = "修改套餐评论信息")
    @RequestMapping(value = "/comment", method = RequestMethod.PATCH)
    public Result<MealJudge> modifyMealJudge(@RequestBody MealJudge base) {
        return showGoodsClient.modifyMealJudge(base);
    }

    @ApiOperation(value = "获取套餐评论信息列表", notes = "获取套餐评论信息列表")
    @RequestMapping(value = "/comment/page", method = RequestMethod.POST)
    public Result<PageGrid<MealJudgeVo>> MealJudgePage(@RequestBody PageParam<MealJudge> base) {
        return showGoodsClient.mealJudgePage(base);
    }
}
