package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.CounselInfo;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.base.ProductContent;
import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.FileUtils;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * @author Administrator
 */
@Api(tags = {"官网管理：WebsiteController"})
@RestController
@RequestMapping(value = "/v1/webs")
public class WebsiteController {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private FtpServer ftpServer;

    @PostMapping(value = "/product/page")
    public Result<PageGrid<ProductInfo>> getProductPage(@RequestBody PageParam<ProductInfo> base) {
        return showBaseClient.getProductPage(base);
    }

    @PostMapping(value = "/product")
    public Result<ProductInfo> addProduct(@RequestBody @Validated ProductInfo base) {
        return showBaseClient.addProduct(base);
    }

    @GetMapping(value = "/product/{markId}")
    public Result<ProductInfo> getProductInfo(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getProductInfo(markId);
    }

    @PatchMapping(value = "/product")
    public Result<ProductInfo> editProduct(@RequestBody @Validated ProductInfo base) {
        return showBaseClient.editProduct(base);
    }

    @GetMapping(value = "/content/{productId}")
    public Result<ProductContent> getProductContent(@PathVariable("productId") @NotBlank String productId) {
        return showBaseClient.getProductContent(productId);
    }

    @PatchMapping(value = "/content")
    public Result<ProductContent> editProductContent(@RequestBody @Validated ProductContent base) {
        return showBaseClient.editProductContent(base);
    }

    @PostMapping(value = "/content")
    public Result<String> contentUpload(
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ShowAssert.checkTrue((ObjectUtil.isNull(file) || file.isEmpty()), StatusCode._4009);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        ImageInfo imageInfo = ImageInfo.builder().markId(snowflake.nextIdStr()).build();
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        ShowAssert.checkTrue(!FileUtils.isImgSuffix(fileType), StatusCode._5005);
        String savePath = "/" + DateUtil.today() + "/";
        String destFileName = imageInfo.getMarkId() + fileType;
        ftpServer.upload(savePath, destFileName, file.getInputStream());
        imageInfo.setImagePath(savePath);
        imageInfo.setFileType(fileType.substring(fileType.lastIndexOf(".") + 1));
        showBaseClient.addImgInfo(imageInfo);
        return new Result<>(Contacts.IMAGE_SERVER + "/shows/" + imageInfo.getMarkId());
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.today());
    }

    @PostMapping(value = "/counsel/page")
    Result<PageGrid<CounselInfo>> getCounselPage(@RequestBody PageParam<CounselInfo> base) {
        return showBaseClient.getCounselPage(base);
    }
}
