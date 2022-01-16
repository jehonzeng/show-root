package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Validated
@Api(tags = { "图片展示:ShowController" })
@RestController
@RequestMapping(value = "shows")
public class ShowController {

    @Resource
    private ShowBaseClient showBaseClient;

    private final static String IMAGE_SERVER = "https://image.lujishou.com/image";

    /**
     * 直接根据图片id获取图片 （默认）
     *
     * @param response
     * @param markId
     * @throws IOException
     * @date 2019年6月28日
     */
    @ApiOperation(value = "直接根据图片id获取图片 （默认） ", notes = "直接根据图片id获取图片 （默认） ")
    @GetMapping(value = "/{markId}")
    public void showDefImage(HttpServletResponse response, @PathVariable("markId") @NotBlank String markId) throws IOException {
        Result<ImageInfo> result = showBaseClient.showImage(markId);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//        showImg(response, image, Contacts.OTHER_DEF_IMG_SIZE, Contacts.OTHER_DEF_IMG_SIZE, 0);
    }

    /**
     * 直接根据图片id显示图片
     *
     * @date 2019年3月22日 下午2:22:52
     * @param response
     * @param markId
     * @param width
     * @param height
     * @param blankStatus
     * @throws IOException
     */
    @ApiOperation(value = "直接根据图片id显示自定义宽高图片 ", notes = "直接根据图片id显示自定义宽高图片")
    @GetMapping(value = "/{markId}/{width}/{height}/{blankStatus}")
    public void showImage(HttpServletResponse response, @PathVariable("markId") @NotBlank String markId,
            @PathVariable("width") Integer width, @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) throws IOException {
        Result<ImageInfo> result = showBaseClient.showImage(markId);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//        showImg(response, image, width, height, blankStatus);
    }

    /*private void showImg(HttpServletResponse response, ImageInfo image, Integer width, Integer height,
            Integer blankStatus) {
        FTPClient ftp = ftpServer.getFtpClient();
        InputStream in = null;
        OutputStream os = null;
        try {
            String path = ftpServer.getBasePath() + image.getImagePath();
            ftp.enterLocalPassiveMode();
            in = ftp.retrieveFileStream(path);
            if (in == null) {
                path = ftpServer.getBasePath() + "/" + Contacts.DEFAULT_IMG;
                ftp.enterLocalPassiveMode();
                in = ftp.retrieveFileStream(path);
            }
            // 压缩后图片字节
            BufferedImage resizeImage = ImageUtils.resize(ImageIO.read(in), width, height, blankStatus > 0);
            // 将压缩后缓存图片写入字节数组输出流中
            byte[] imageByte = ImageUtils.translateImage(resizeImage, image.getFileType());
            response.setContentType("image/" + image.getFileType());
            os = response.getOutputStream();
            os.write(imageByte);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }*/
}
