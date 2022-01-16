package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.qrcode.QrcodeTicket;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/qrcode")
public class WxaCodeController {

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private FtpServer ftpServer;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "创建桌台二维码")
    @PostMapping(value = "/table/create")
    public Result<?> createCode(@RequestParam("tableId") @NotBlank String tableId) {
        // 创建永久二维码
        QrcodeTicket qrcodeTicket = createQrcode(tableId);
        ImageInfo imageInfo = new ImageInfo();
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        imageInfo.setMarkId(snowflake.nextIdStr());
        try {
            String path = "/" + DateUtil.today() + "/" + tableId + "/";
            String fileName = imageInfo.getMarkId() + ".png";
            BufferedImage bufferedImage = QrcodeAPI.showqrcode(qrcodeTicket.getTicket());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            ImageIO.write(bufferedImage, "png", ios);
            InputStream in = new ByteArrayInputStream(os.toByteArray());
            ftpServer.upload(path, fileName, in);
            imageInfo.setImagePath(path + fileName);
            imageInfo.setFileType("png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        showBaseClient.addImgInfo(imageInfo);
//        String responseUrl = "https://image.lujishou.com/image"+ imageInfo.getImagePath();
        return new Result<>(qrcodeTicket.getUrl());
    }

    private QrcodeTicket createQrcode(String tableId) {
        QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateFinal(wechatConfig.getToken(),
                tableId);
        if (qrcodeTicket == null || !qrcodeTicket.isSuccess()
                || qrcodeTicket.getErrcode().equals("40001")) {
            wechatConfig.refreshToken();
            qrcodeTicket = QrcodeAPI.qrcodeCreateFinal(wechatConfig.getToken(),
                    tableId);
        }
        return qrcodeTicket;
    }
}
