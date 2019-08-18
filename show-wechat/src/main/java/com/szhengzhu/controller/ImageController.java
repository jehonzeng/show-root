package com.szhengzhu.controller;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.FileUtils;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"图片处理：ImageController"})
@RestController
@RequestMapping("/v1/images")
public class ImageController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "图片上传", notes = "图片上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result<String> upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setMarkId(IdGenerator.getInstance().nexId());
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            if (FileUtils.isImgSuffix(fileType)) {
                String savePath =  File.separator + TimeUtils.textDate() + File.separator + imageInfo.getMarkId() + fileType;
                FileUtils.uploadImg(file, Contacts.BASE_IMG_PATH + savePath);
                imageInfo.setImagePath(savePath);
                imageInfo.setFileType(fileType.substring(fileType.lastIndexOf(".") + 1));
            } else {
                return new Result<>(StatusCode._5005);
            }
        }
        showBaseClient.addImgInfo(imageInfo);
        return new Result<>(imageInfo.getMarkId());
    }
}
