package com.szhengzhu.controller;

import java.io.File;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.util.ImageUtils;

import io.swagger.annotations.Api;

@Api(tags = { "图片展示:ShowController" })
@RestController
@RequestMapping(value = "shows")
public class ShowController {

    @Resource
    private ShowBaseClient showBaseClient;

    /**
     * 获取不同类型小展示图和规格图
     * 
     * @param goodsId
     * @param specIds
     * @param type
     *            (0:普通商品 1:商品券 2:套餐 3:附属品)
     * @date 2019年6月28日
     */
    @RequestMapping(value = "/{type}/{goodsId}", method = RequestMethod.GET)
    public void showImageByType(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId, @PathVariable("type") Integer type,
            @RequestParam(value = "specIds", required = false) String specIds) {
        ImageInfo image = null;
        switch (type.intValue()) {
        case 0:
            image = showBaseClient.showGoodsSpecImage(goodsId, specIds);// goodsId=>普通商品id
            break;
        case 1:
            image = showBaseClient.showVoucherSpecImage(goodsId);// goodsId=>商品券id
            break;
        case 2:
            image = showBaseClient.showMealSmallImage(goodsId);// goodsId=> 套餐id
            break;
        case 3:
            image = showBaseClient.showAccessorylImage(goodsId);// goodsId=> 附属品id
            break;
        }
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
    }

    /**
     * 获取小展示图 （默认）
     * 
     * @date 2019年3月14日 上午10:52:26
     * @param response
     * @param goodsId
     * @param type
     * @param width
     * @param height
     * @param blankStatus
     */
    @RequestMapping(value = "/small/{goodsId}", method = RequestMethod.GET)
    public void showSmallDefImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 0, null);
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
    }

    /**
     * 获取小展示图
     *
     * @param response
     * @param goodsId
     * @param width
     * @param height
     * @param blankStatus
     * @date 2019年7月1日
     */
    @RequestMapping(value = "/small/{goodsId}/{width}/{height}/{blankStatus}", method = RequestMethod.GET)
    public void showSmallImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId, @PathVariable("width") Integer width,
            @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 0, null);
        showImg(response, image, width, height, blankStatus);
    }

    /**
     * 获取大展示图 （默认）
     *
     * @param response
     * @param goodsId
     * @date 2019年7月1日
     */
    @RequestMapping(value = "/large/{goodsId}", method = RequestMethod.GET)
    public void showBigDefImage(HttpServletResponse response,
            @PathVariable("goodsId") String goodsId) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 1, null);
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
    }

    /**
     * 获取大展示图
     *
     * @param response
     * @param goodsId
     * @param width
     * @param height
     * @param blankStatus
     * @date 2019年7月1日
     */
    @RequestMapping(value = "/large/{goodsId}/{width}/{height}/{blankStatus}", method = RequestMethod.GET)
    public void showBigImage(HttpServletResponse response, @PathVariable("goodsId") String goodsId,
            @PathVariable("width") Integer width, @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 1, null);
        showImg(response, image, width, height, blankStatus);
    }

    /**
     * 获取规格图 （默认）
     *
     * @param response
     * @param goodsId
     * @param specIds
     * @date 2019年7月1日
     */
    @RequestMapping(value = "/spec/{goodsId}", method = RequestMethod.GET)
    public void showSpecImage(HttpServletResponse response, @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "specIds", required = false) String specIds) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 2, specIds);
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
    }

    /**
     * 获取规格图
     *
     * @param response
     * @param goodsId
     * @param specIds
     * @param width
     * @param height
     * @param blankStatus
     * @date 2019年7月1日
     */
    @RequestMapping(value = "/spec/{goodsId}/{width}/{height}/{blankStatus}", method = RequestMethod.GET)
    public void showSpecImage(HttpServletResponse response, @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "specIds", required = false) String specIds,
            @PathVariable("width") Integer width, @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) {
        ImageInfo image = showBaseClient.showGoodsImage(goodsId, 2, specIds);
        showImg(response, image, width, height, blankStatus);
    }

    /**
     * 根据imagePath显示首页图 （默认）
     *
     * @param response
     * @param imagePath
     * @date 2019年6月28日
     */
    @RequestMapping(value = "/index/{imagePath}", method = RequestMethod.GET)
    public void showDefIndexImage(HttpServletResponse response,
            @PathVariable("imagePath") String imagePath) {
        ImageInfo image = showBaseClient.showImage(imagePath);
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
    }

    /**
     * 根据imagePath显示首页图 imagePath:图片路径id
     * 
     * @date 2019年3月15日 上午11:04:17
     * @param response
     * @param imagePath
     * @param width
     * @param height
     * @param blankStatus
     */
    @RequestMapping(value = "/index/{imagePath}/{width}/{height}/{blankStatus}", method = RequestMethod.GET)
    public void showIndexDefImage(HttpServletResponse response,
            @PathVariable("imagePath") String imagePath, @PathVariable("width") Integer width,
            @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) {
        ImageInfo image = showBaseClient.showImage(imagePath);
        showImg(response, image, width, height, blankStatus);
    }

    /**
     * 直接根据图片id获取图片 （默认）
     *
     * @param response
     * @param markId
     * @date 2019年6月28日
     */
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public void showDefImage(HttpServletResponse response, @PathVariable("markId") String markId) {
        ImageInfo image = showBaseClient.showImage(markId);
        showImg(response, image, Contacts.DEF_IMG_SIZE, Contacts.DEF_IMG_SIZE, 0);
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
     */
    @RequestMapping(value = "/{markId}/{width}/{height}/{blankStatus}", method = RequestMethod.GET)
    public void showImage(HttpServletResponse response, @PathVariable("markId") String markId,
            @PathVariable("width") Integer width, @PathVariable("height") Integer height,
            @PathVariable("blankStatus") Integer blankStatus) {
        ImageInfo image = showBaseClient.showImage(markId);
        showImg(response, image, width, height, blankStatus);
    }

    /**
     * 公共方法处理改变图片 显示图片
     *
     * @param response
     * @param image
     * @param width
     * @param height
     * @param blankStatus
     * @date 2019年7月1日
     */
    private void showImg(HttpServletResponse response, ImageInfo image, Integer width,
            Integer height, Integer blankStatus) {
        try {
            File file = new File(image.getImagePath());
            if (!file.exists())
                file = new File(Contacts.BASE_IMG_PATH + File.separator + "default_hands.png");
            ImageUtils.resize(ImageIO.read(file), width, height, blankStatus > 0);
            response.setContentType("image/" + image.getFileType());
            OutputStream os = response.getOutputStream();
            ImageIO.write(ImageIO.read(file), image.getFileType(), os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
