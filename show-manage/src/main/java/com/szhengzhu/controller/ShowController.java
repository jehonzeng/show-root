package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.vo.OrderExportVo;
import com.szhengzhu.config.FtpServer;
import com.szhengzhu.context.OrderContext;
import com.szhengzhu.context.ProductContext;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.handler.AbstractProduct;
import com.szhengzhu.util.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author terry shi
 */
@Api(tags = { "图片展示:ShowController" })
@RestController
@RequestMapping(value = "shows")
public class ShowController {

    @Resource
    private FtpServer ftpServer;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ProductContext productContext;

    @Resource
    private OrderContext orderContext;

    private final static String IMAGE_SERVER = "https://image.lujishou.com/image/";

    /**
     * 获取不同类型小展示图和规格图
     *
     * @param goodsId
     * @param specIds
     * @param type
     *            (0:普通商品 1:商品券 2:套餐 3:附属品)
     * @throws IOException
     * @date 2019年6月28日
     */
    @ApiOperation(value = "获取不同类型小展示图或规格图", notes = "获取不同类型小展示图或规格图")
    @GetMapping(value = "/{type}/{goodsId}")
    public void showImageByType(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId, @PathVariable("type") Integer type,
            @RequestParam(value = "specIds", required = false) String specIds) throws IOException {
        AbstractProduct handler = productContext.getInstance(type.toString());
        Result<ImageInfo> result = handler.showProductImg(goodsId, specIds);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//        showImg(response, image, Contacts.GOODS_DEF_IMG_SIZE, Contacts.GOODS_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 200, 1);
    }

    /**
     * 获取小展示图 （默认）
     *
     * @date 2019年3月14日 上午10:52:26
     * @param response
     * @param goodsId
     * @throws IOException
     */
    @ApiOperation(value = "获取小展示图 （默认）", notes = "获取小展示图 （默认）")
    @GetMapping(value = "/small/{goodsId}")
    public void showSmallDefImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId) throws IOException {

        Result<ImageInfo> result = showBaseClient.showGoodsImage(goodsId, 0, null);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//        showImg(response, image, Contacts.GOODS_DEF_IMG_SIZE, Contacts.GOODS_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 200, 1);
    }

    // /**
    // * 获取小展示图
    // *
    // * @param response
    // * @param goodsId
    // * @param width
    // * @param height
    // * @param blankStatus
    // * @date 2019年7月1日
    // */
    // @ApiOperation(value = "获取自定义宽高的小展示图 ", notes = "获取自定义宽高的小展示图 ")
    // @GetMapping(value = "/small/{goodsId}/{width}/{height}/{blankStatus}")
    // public void showSmallImage(HttpServletResponse response,
    // @PathVariable("goodsId") String goodsId,
    // @PathVariable("width") Integer width, @PathVariable("height") Integer height,
    // @PathVariable("blankStatus") Integer blankStatus) {
    // ImageInfo image = showBaseClient.showGoodsImage(goodsId, 0, null);
    // showImg(response, image, width, height, blankStatus);
    // }

    /**
     * 获取大展示图 （默认）16:9
     *
     * @param response
     * @param goodsId
     * @throws IOException
     * @date 2019年7月1日
     */
    @ApiOperation(value = "获取大展示图 (默认)", notes = "获取大展示图 (默认)")
    @GetMapping(value = "/large/{goodsId}")
    public void showBigDefImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId) throws IOException {
        Result<ImageInfo> result = showBaseClient.showGoodsImage(goodsId, 1, null);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//        showImg(response, image, Contacts.GOODS_DEF_IMG_SIZE, Contacts.GOODS_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 320, 16 / 9);
    }

//    /**
//     * 图片压缩输出显示
//     *
//     * @param response
//     * @param image
//     * @param comBase
//     * @param scale
//     */
//    private void displayImage(HttpServletResponse response, ImageInfo image, double comBase,
//            double scale) {
//        FTPClient ftp = ftpServer.getFtpClient();
//        InputStream in = null;
//        OutputStream os = null;
//        try {
//            String path = ftpServer.getBasePath() + image.getImagePath();
//            System.out.println(path);
//            in = ftp.retrieveFileStream(path);
//            BufferedImage resizeImage = ImageUtils.resize(ImageIO.read(in), comBase, scale);
//            response.setContentType("image/" + image.getFileType());
//            os = response.getOutputStream();
//            ImageIO.write(resizeImage, image.getFileType(), os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//            }
//        }
//    }

    // /**
    // * 获取大展示图16:9
    // *
    // * @param response
    // * @param goodsId
    // * @param width
    // * @param height
    // * @param blankStatus
    // * @date 2019年7月1日
    // */
    // @ApiOperation(value = "获取自定义宽高的大展示图 ", notes = "获取自定义宽高的大展示图 ")
    // @GetMapping(value = "/large/{goodsId}/{width}/{height}/{blankStatus}")
    // public void showBigImage(HttpServletResponse response,
    // @PathVariable("goodsId") String goodsId,
    // @PathVariable("width") Integer width, @PathVariable("height") Integer height,
    // @PathVariable("blankStatus") Integer blankStatus) {
    // ImageInfo image = showBaseClient.showGoodsImage(goodsId, 1, null);
    // showImg(response, image, width, height, blankStatus);
    // }

    /**
     * 获取规格图 （默认）1:1
     *
     * @param response
     * @param goodsId
     * @param specIds
     * @throws IOException
     * @date 2019年7月1日
     */
    @ApiOperation(value = "获取规格图 （默认）", notes = "获取规格图 （默认）")
    @GetMapping(value = "/spec/{goodsId}")
    public void showSpecImage(HttpServletResponse response, @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "specIds", required = false) String specIds) throws IOException {
        Result<ImageInfo> result = showBaseClient.showGoodsImage(goodsId, 2, specIds);
        String imagePath = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + imagePath);
//
//        showImg(response, image, Contacts.GOODS_DEF_IMG_SIZE, Contacts.GOODS_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 200, 1);
    }

    // /**
    // * 获取规格图1:1
    // *
    // * @param response
    // * @param goodsId
    // * @param specIds
    // * @param width
    // * @param height
    // * @param blankStatus
    // * @date 2019年7月1日
    // */
    // @ApiOperation(value = "获取自定义的规格图 ", notes = "获取自定义的规格图 ")
    // @GetMapping(value = "/spec/{goodsId}/{width}/{height}/{blankStatus}")
    // public void showSpecImage(HttpServletResponse response,
    // @PathVariable("goodsId") String goodsId,
    // @RequestParam(value = "specIds", required = false) String specIds,
    // @PathVariable("width") Integer width,
    // @PathVariable("height") Integer height, @PathVariable("blankStatus") Integer
    // blankStatus) {
    // ImageInfo image = showBaseClient.showGoodsImage(goodsId, 2, specIds);
    // showImg(response, image, width, height, blankStatus);
    // }

    /**
     * 根据imagePath显示首页图 （默认）
     *
     * @param response
     * @param imagePath
     * @throws IOException
     * @date 2019年6月28日
     */
    @ApiOperation(value = "根据imagePath显示首页图 （默认） ", notes = "根据imagePath显示首页图 （默认） ")
    @GetMapping(value = "/index/{imagePath}")
    public void showDefIndexImage(HttpServletResponse response,
            @PathVariable("imagePath") String imagePath) throws IOException {

        Result<ImageInfo> result = showBaseClient.showImage(imagePath);
        String path = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + path);

//        showImg(response, image, Contacts.OTHER_DEF_IMG_SIZE, Contacts.OTHER_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 1000, 1);
    }

    // /**
    // * 根据imagePath显示首页图 imagePath:图片路径id
    // *
    // * @date 2019年3月15日 上午11:04:17
    // * @param response
    // * @param imagePath
    // * @param width
    // * @param height
    // * @param blankStatus
    // */
    // @ApiOperation(value = "根据imagePath显示自定义宽高首页图 ", notes =
    // "根据imagePath显示自定义宽高首页图 ")
    // @GetMapping(value = "/index/{imagePath}/{width}/{height}/{blankStatus}")
    // public void showIndexImage(HttpServletResponse response,
    // @PathVariable("imagePath") String imagePath,
    // @PathVariable("width") Integer width, @PathVariable("height") Integer height,
    // @PathVariable("blankStatus") Integer blankStatus) {
    // ImageInfo image = showBaseClient.showImage(imagePath);
    // showImg(response, image, width, height, blankStatus);
    // }

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
    public void showDefImage(HttpServletResponse response, @PathVariable("markId") String markId) throws IOException {
        Result<ImageInfo> result = showBaseClient.showImage(markId);
        String path = result.getData().getImagePath();
        response.sendRedirect(IMAGE_SERVER + path);
//        showImg(response, image, Contacts.OTHER_DEF_IMG_SIZE, Contacts.OTHER_DEF_IMG_SIZE, 0);
        // displayImage(response, image, 1000, 1);
    }

    // /**
    // * 直接根据图片id显示图片
    // *
    // * @date 2019年3月22日 下午2:22:52
    // * @param response
    // * @param markId
    // * @param width
    // * @param height
    // * @param blankStatus
    // */
    // @ApiOperation(value = "直接根据图片id显示自定义宽高图片 ", notes = "直接根据图片id显示自定义宽高图片")
    // @GetMapping(value = "/{markId}/{width}/{height}/{blankStatus}", method =)
    // public void showImage(HttpServletResponse response, @PathVariable("markId")
    // String markId,
    // @PathVariable("width") Integer width, @PathVariable("height") Integer height,
    // @PathVariable("blankStatus") Integer blankStatus) {
    // ImageInfo image = showBaseClient.showImage(markId);
    // showImg(response, image, width, height, blankStatus);
    // }

//    /**
//     * 公共方法处理改变图片 显示图片
//     *
//     * @param response
//     * @param image
//     * @param width
//     * @param height
//     * @param blankStatus
//     * @date 2019年7月1日
//     */
//    private void showImg(HttpServletResponse response, ImageInfo image, Integer width,
//            Integer height, Integer blankStatus) {
//        FTPClient ftp = ftpServer.getFtpClient();
//        InputStream in = null;
//        OutputStream os = null;
//        try {
//            String path = ftpServer.getBasePath() + image.getImagePath();
//            System.out.println(path);
//            ftp.enterLocalPassiveMode();
//            in = ftp.retrieveFileStream(path);
//            if (in ==null) {
//                path = ftpServer.getBasePath() + "/" + Contacts.DEFAULT_IMG;
//                in = ftp.retrieveFileStream(path);
//            }
//            // 压缩后图片字节
//            BufferedImage resizeImage = ImageUtils.resize(ImageIO.read(in), width, height,
//                    blankStatus > 0);
//            ftp.completePendingCommand();
//            // 将压缩后缓存图片写入字节数组输出流中
//            byte[] imageByte = ImageUtils.translateImage(resizeImage, image.getFileType());
//            response.setContentType("image/" + image.getFileType());
//            os = response.getOutputStream();
//            os.write(imageByte);
//            os.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//            }
//        }
//    }

    @ApiOperation(value = "打印普通订单图片 ", notes = "打印普通订单图片")
    @GetMapping(value = "/print/{markId}")
    public Result printCommonOrder(HttpServletResponse response,
            @PathVariable("markId") String markId) {
        Result<OrderExportVo> result = showOrderClient.getExportOrderInfo(markId);
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4021);
        InputStream imageIn = null;
        InputStream in = null;
        OutputStream out = null;
//        FTPClient ftpClient = ftpServer.getFtpClient();
        try {
            imageIn = getClass().getClassLoader().getResourceAsStream("back.png");
//            String path = ftpServer.getBasePath() + "/" + "back.png";
//            ftpClient.enterLocalPassiveMode();
            // 获取ftp中图片输入流
//            imageIn = ftpClient.retrieveFileStream(path);
            BufferedImage bufferedImage = ImageUtils.createOrderImage(result.getData(), imageIn);
//            ftpClient.completePendingCommand();
            byte[] imageByte = ImageUtils.translateImage(bufferedImage);
            // 获取编辑好的图片流，输出到前端
            in = new ByteArrayInputStream(imageByte);
            response.setContentType("image/png");
            out = response.getOutputStream();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageIn != null) {
                try {
                    imageIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//            }
        }
        return new Result<>();
    }

    /**
     *
     *
     * @param response
     * @param markId
     * @param type
     *            订单类型(1:普通订单, 2: 团购 3：秒杀）
     * @return
     * @throws IOException
     * @date 2019年9月5日
     */
    @ApiOperation(value = "打印(普通，团购，秒杀)订单图片 ", notes = "打印(普通，团购，秒杀)订单图片")
    @GetMapping(value = "/print/{type}/{markId}")
    public Result<?> printOrder(HttpServletResponse response, @PathVariable("markId") String markId,
            @PathVariable("type") Integer type) {
        AbstractOrder handler = orderContext.getInstance(type.toString());
        Result<?> result = handler.getExportOrderInfo(markId);
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4021);

        InputStream imageIn = null;
        InputStream in = null;
        OutputStream out = null;
//        FTPClient ftpClient = ftpServer.getFtpClient();
        try {
            imageIn = getClass().getClassLoader().getResourceAsStream("back.png");
            // 获取ftp中图片输入流
//            String path = ftpServer.getBasePath() + "/" + "back.png";
//            ftpClient.enterLocalPassiveMode();
//            imageIn = ftpClient.retrieveFileStream(path);
            BufferedImage bufferedImage = handler.createExportImage(result.getData(), imageIn);
//            ftpClient.completePendingCommand();
            byte[] imageByte = ImageUtils.translateImage(bufferedImage);
            response.setContentType("image/png");
            in = new ByteArrayInputStream(imageByte);
            out = response.getOutputStream();
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageIn != null) {
                try {
                    imageIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                    ioe.printStackTrace();
//                }
//            }
        }
        return new Result<>();
    }
}
