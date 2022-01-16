package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Validated
@Api(tags = {"图片处理:ImageController"})
@RestController
@RequestMapping(value = "/v1/images")
public class ImageController {

    @Resource
    private FtpServer ftpServer;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/upload")
    public Result<String> upload(
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ShowAssert.checkTrue((ObjectUtil.isNull(file) || file.isEmpty()), StatusCode._4009);
        ImageInfo imageInfo = new ImageInfo();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        imageInfo.setMarkId(snowflake.nextIdStr());
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        ShowAssert.checkTrue(!FileUtils.isImgSuffix(fileType), StatusCode._5005);
        String savePath = "/" + DateUtil.today() + "/";
        String newFileName = imageInfo.getMarkId() + fileType;
        boolean flag = ftpServer.upload(savePath, newFileName, file.getInputStream());
        ShowAssert.checkTrue(!flag, StatusCode._4008);
        imageInfo.setImagePath(savePath + newFileName);
        imageInfo.setFileType(fileType.substring(fileType.lastIndexOf(".") + 1));
        showBaseClient.addImgInfo(imageInfo);
        return new Result<>(imageInfo.getMarkId());
    }
}
