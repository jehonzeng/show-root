package com.szhengzhu.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.vo.OrderExportVo;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 */
public final class ImageUtils {

    private ImageUtils() {
    }

    /**
     * 添加图片水印
     *
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg
     *            水印图片路径，如：C://myPictrue//logo.png
     * @param x
     *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y
     *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static BufferedImage pressImage(String targetImg, String waterImg, int x, int y,
            float alpha) {
        File file = new File(targetImg);
        return pressImage(file, waterImg, x, y, alpha);
    }

    public static BufferedImage pressImage(File file, String waterImg, int x, int y, float alpha) {
        try {
            BufferedImage image = ImageIO.read(file);
            return pressImage(image, waterImg, x, y, alpha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage pressImage(BufferedImage image, String waterImg, int x, int y,
            float alpha) {
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
            int width_1 = waterImage.getWidth(null);
            int height_1 = waterImage.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }
            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
            g.dispose();
            return bufferedImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加文字水印
     *
     * @param targetImg
     *            目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText
     *            水印文字， 如：中国证券网
     * @param fontName
     *            字体名称， 如：宋体
     * @param fontStyle
     *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize
     *            字体大小，单位为像素
     * @param color
     *            字体颜色
     * @param x
     *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y
     *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static BufferedImage pressText(String targetImg, String pressText, String fontName,
            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        File file = new File(targetImg);
        return pressText(file, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
    }

    public static BufferedImage pressText(File file, String pressText, String fontName,
            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            BufferedImage image = ImageIO.read(file);
            return pressText(image, pressText, fontName, fontStyle, fontSize, color, x, y, alpha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static BufferedImage pressText(BufferedImage image, String pressText, String fontName,
            int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }

            g.drawString(pressText, x, y + height_1);
            g.dispose();
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     *
     * @param filePath
     *            图片路径
     * @param height
     *            高度
     * @param width
     *            宽度
     * @param bb
     *            比例不对时是否需要补白
     */
    public static BufferedImage resize(String filePath, int width, int height, boolean bb) {
        File filse = new File(filePath);
        return resize(filse, width, height, bb);
    }

    public static BufferedImage resize(File file, int width, int height, boolean bb) {
        try {
            BufferedImage bi = ImageIO.read(file);
            return resize(bi, width, height, bb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按文件的大小缩放缩放
     *
     * @param file
     * @param size 文件的大小
     * @return
     * @throws Exception
     */
    public static BufferedImage resize(File file, Integer size) throws Exception {
        long fileSize = file.length();
        // 文件大于size k时，才进行缩放,注意：size以K为单位
        BufferedImage bi = ImageIO.read(file);
        if (fileSize < size * 1024) {
            return bi;
        }
        //获取图片的宽高
        int width = bi.getWidth(null);
        int height = bi.getHeight(null);
        // 获取长宽缩放比例
        double rate = (size * 1024 * 0.5) / fileSize;
        Image itemp = bi.getScaledInstance(width, height,BufferedImage.SCALE_SMOOTH);
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(rate, rate),
                null);
        itemp = ato.filter(bi, null);
        return (BufferedImage) itemp;
    }

    /**
     * 等比例压缩算法：
     * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     * @param bi 原图
     * @param comBase 压缩基数
     * @param scale 压缩限制(宽/高)比例  一般用16:9 = 1.78 | 1:1 = 1 |
     * 当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     */
    public static BufferedImage resize(BufferedImage bi, double comBase,double scale) throws Exception {
        int srcHeight = bi.getHeight(null);
        int srcWidth = bi.getWidth(null);
        // 缩略图高
        int deskHeight = 0;
        // 缩略图宽
        int deskWidth = 0;
        double srcScale = (double) srcHeight / srcWidth;
        if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
            if (srcScale >= scale || 1 / srcScale > scale) {
                if (srcScale >= scale) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            } else {
                if ((double) srcHeight > comBase) {
                    deskHeight = (int) comBase;
                    deskWidth = srcWidth * deskHeight / srcHeight;
                } else {
                    deskWidth = (int) comBase;
                    deskHeight = srcHeight * deskWidth / srcWidth;
                }
            }
        } else {
            deskHeight = srcHeight;
            deskWidth = srcWidth;
        }
        BufferedImage image = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
        //绘制缩小后的图
        image.getGraphics().drawImage(bi, 0, 0, deskWidth, deskHeight, null);
        return image;
    }

    /**
     * 按照固定宽高尺寸压缩图片
     *
     * @param bi
     * @param width 指定图片宽
     * @param height 指定图片高
     * @param bb 图片是否留白
     * @return
     */
    public static BufferedImage resize(BufferedImage bi, int width, int height, boolean bb) {
        double ratio = 0; // 缩放比例
        Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
            if (bi.getHeight() > bi.getWidth()) {
                ratio = (new Integer(height)).doubleValue() / bi.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue() / bi.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(bi, null);
        } else {
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(1, 1),
                    null);
            itemp = op.filter(bi, null);
            return (BufferedImage) itemp;
        }
        if (bb) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null))
                graphics.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                        itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            else
                graphics.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                        itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            graphics.dispose();
            return image;
        } else {
            return (BufferedImage) itemp;
        }
    }

    public static byte[] translateImage(BufferedImage bImage, String type) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, type, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] translateImage(BufferedImage bufferedImage){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
            ImageIO.write(bufferedImage, "png", ios);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static BufferedImage mergeImage(BufferedImage background, BufferedImage QRcode) {
        int width = background.getWidth(null);
        int height = background.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        int[] pos = position(background, width, height);
        g.drawImage(background, 0, 0, width, height, null);
        g.drawImage(QRcode, pos[0], pos[1], pos[2], pos[2], null); // 水印文件结束
        g.dispose();
        return bufferedImage;
    }

    private static int[] position(BufferedImage bi, int w, int h) {
        int[] position = { 0, 0, 0 };
        boolean is_start = false;
        int t = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb = bi.getRGB(i, j);
                int red = rgb >> 16 & 0xFF;
                int green = rgb >> 8 & 0xFF;
                int blue = rgb & 0xFF;
                if (blue + green + red == 0) {
                    t++;
                    System.out.println(t);
                }
                if (rgb > 0) {
                    if (!is_start) {
                        position[0] = i;
                        position[1] = j;
                        is_start = true;
                    }
                } else {
                    if (is_start) {
                        position[2] = j - position[1];
                        return position;
                    }
                }

            }
        }
        return position;
    }

    /**
     * 导单处理
     *
     * @param order
     * @return
     * @date 2019年9月2日
     */
    // public static BufferedImage createOrderImage(OrderExportVo order) {
    // try {
    // InputStream imagein = new FileInputStream(File.separator + "backend"
    // + File.separator + "pictures" + File.separator
    // + "back.png");
    // BufferedImage background = ImageIO.read(imagein);
    // Graphics2D g = background.createGraphics();
    // Font f = new Font("宋体", Font.BOLD, 35);
    // g.setFont(f);
    // g.setColor(Color.BLACK);
    // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    // RenderingHints.VALUE_ANTIALIAS_ON);
    // int start = 70, width = 640, left = 25;
    // start = drawBaseInfo(order, g, f, left, start, width);
    // start = drawOrderInfo(order.getOrders(), g, left, start);
    // return background;
    // } catch (IOException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
    //
    // private static int drawBaseInfo(OrderExportVo o, Graphics2D g, Font f,
    // int left, int start, int width) {
    // g.drawString("地址：" + o.getUserArea(), left, start);
    // List<String> rows = getStringRows(g, f, o.getUserAddress(), width);
    // for (int i = 0, len = rows.size(); i < len; i++)
    // g.drawString(rows.get(i), left, start += 36);
    // g.drawString("联系人：" + o.getUserName(), left, start += 42);
    // g.drawString("联系电话：" + o.getUserPhone(), left, start += 42);
    // g.drawString("订单详情：", left, start += 42);
    // return start;
    // }
    //
    // private static int drawOrderInfo(List<OrderItem> list, Graphics g,
    // int left, int start) {
    // String t;
    // for (OrderItem o : list) {
    // t = o.getProductName();
    // t = t.length() <= 15 ? t : t.substring(0, 15);
    // g.drawString(t + "*" + o.getQuantity(), left, start += 36);
    // }
    // return start;
    // }
    //
    // private static List<String> getStringRows(Graphics g, Font font,
    // String text, int width) {
    // List<String> textRows = new LinkedList<String>();
    // String temp;
    // FontMetrics fm = g.getFontMetrics();
    // int text_length = (int) fm.getStringBounds(text, g).getWidth();
    // if (text_length <= width) {
    // textRows.add(text);
    // return textRows;
    // }
    // while (!text.equals("")) {
    // temp = getStringRow(g, fm, text, width);
    // textRows.add(temp);
    // text = text.replaceFirst(temp, "");
    // }
    // return textRows;
    // }
    //
    // private static String getStringRow(Graphics g, FontMetrics fm, String text,
    // int width) {
    // int text_length = (int) fm.getStringBounds(text, g).getWidth();
    // if (text_length <= width)
    // return text;
    // int start = text.length() / (text_length / width + 1);
    // text_length = (int) fm.getStringBounds(text, 0, start, g).getWidth();
    // if (text_length == width)
    // return text.substring(0, start);
    // else if (text_length > width) {
    // while (text_length > width) {
    // start--;
    // text_length = (int) fm.getStringBounds(text, 0, start, g)
    // .getWidth();
    // }
    // return text.substring(0, start);
    // } else {
    // while (text_length < width) {
    // start++;
    // text_length = (int) fm.getStringBounds(text, 0, start, g)
    // .getWidth();
    // }
    // return text.substring(0, start - 1);
    // }
    // }

    /**
     * 创建打印订单图片
     *
     * @param order
     * @return
     * @date 2019年9月5日
     */
    public static BufferedImage createOrderImage(OrderExportVo order,InputStream imagein) {
        try {
//            InputStream imagein = new FileInputStream(File.separator + "backend" + File.separator
//                    + "pictures" + File.separator + "back.png");
            BufferedImage background = ImageIO.read(imagein);
            Graphics2D g = background.createGraphics();
            Font f = new Font("宋体", Font.BOLD, 35);
            g.setFont(f);
            g.setColor(Color.BLACK);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int start = 70, width = 640, left = 25;
            start = drawBaseInfo(order, g, f, left, start, width);
            start = drawOrderInfo(order.getOrders(), g, left, start);
            return background;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 定义图片主体内容位置
     *
     * @param o
     *            数据
     * @param g
     *            画笔
     * @param f
     *            字体
     * @param left
     *            水平左间距
     * @param start
     *            垂直行间距
     * @param width
     *            行宽
     * @return
     * @date 2019年9月5日
     */
    private static int drawBaseInfo(OrderExportVo o, Graphics2D g, Font f, int left, int start,
            int width) {
        g.drawString("地址：" + o.getUserArea(), left, start);
        List<String> rows = getStringRows(g, f, o.getUserAddress(), width);
        for (int i = 0, len = rows.size(); i < len; i++)
            g.drawString(rows.get(i), left, start += 42);
        g.drawString("联系人：" + o.getUserName(), left, start += 50);
        g.drawString("联系电话：" + o.getUserPhone(), left, start += 50);
        g.drawString("商品详情：", left, start += 50);
        return start;
    }

    private static int drawOrderInfo(List<OrderItem> list, Graphics g, int left, int start) {
        String t;
        for (OrderItem o : list) {
            t = o.getProductName();
            t = t.length() <= 15 ? t : t.substring(0, 15);
            g.drawString(t + "*" + o.getQuantity(), left, start += 42);
        }
        return start;
    }

    private static List<String> getStringRows(Graphics g, Font font, String text, int width) {
        List<String> textRows = new LinkedList<String>();
        String temp;
        FontMetrics fm = g.getFontMetrics();
        int text_length = (int) fm.getStringBounds(text, g).getWidth();
        if (text_length <= width) {
            textRows.add(text);
            return textRows;
        }
        while (!text.equals("")) {
            temp = getStringRow(g, fm, text, width);
            textRows.add(temp);
            text = text.replaceFirst(temp, "");
        }
        return textRows;
    }

    private static String getStringRow(Graphics g, FontMetrics fm, String text, int width) {
        int text_length = (int) fm.getStringBounds(text, g).getWidth();
        if (text_length <= width)
            return text;
        int start = text.length() / (text_length / width + 1);
        text_length = (int) fm.getStringBounds(text, 0, start, g).getWidth();
        if (text_length == width)
            return text.substring(0, start);
        else if (text_length > width) {
            while (text_length > width) {
                start--;
                text_length = (int) fm.getStringBounds(text, 0, start, g).getWidth();
            }
            return text.substring(0, start);
        } else {
            while (text_length < width) {
                start++;
                text_length = (int) fm.getStringBounds(text, 0, start, g).getWidth();
            }
            return text.substring(0, start - 1);
        }
    }

    public static BufferedImage createActivityOrderImage(ExportTemplateVo order,InputStream imagein) {
        try {
//            InputStream imagein = new FileInputStream(File.separator + "backend" + File.separator
//                    + "pictures" + File.separator + "back.png");
            BufferedImage background = ImageIO.read(imagein);
            Graphics2D g = background.createGraphics();
            Font f = new Font("宋体", Font.BOLD, 35);
            g.setFont(f);
            g.setColor(Color.BLACK);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int start = 70, width = 640, left = 25;
            start = drawBaseInfo(order, g, f, left, start, width);
            String t = order.getProductName();
            t = t.length() <= 15 ? t : t.substring(0, 15);
            g.drawString(t + "*" + order.getQuantity(), left, start += 42);
            return background;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int drawBaseInfo(ExportTemplateVo o, Graphics2D g, Font f, int left, int start,
            int width) {
        g.drawString("地址：" + o.getUserArea(), left, start);
        List<String> rows = getStringRows(g, f, o.getUserAddress(), width);
        for (int i = 0, len = rows.size(); i < len; i++)
            g.drawString(rows.get(i), left, start += 42);
        g.drawString("联系人：" + o.getUserName(), left, start += 50);
        g.drawString("联系电话：" + o.getUserPhone(), left, start += 50);
        g.drawString("商品详情：", left, start += 50);
        return start;
    }

    public static void main(String[] args) throws IOException {
        // BufferedImage bufferedImage =
        // pressImage("C:\\Users\\Administrator\\Desktop\\e246c5a7930c72ddaa58319521ac0a7.jpg","D:\\中国心(黑).jpg",130,400,1);
        // BufferedImage bufferedImage =
        // pressImage("C:\\Users\\Administrator\\Desktop\\d00f96036697fb0181f3051521584da.jpg","D:\\中国心(白).png",180,400,1);
        // bufferedimage 转换成 inputstream
        // ByteArrayOutputStream bs = new ByteArrayOutputStream();
        // ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
        // ImageIO.write(bufferedImage, "jpg", imOut);
        // InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
        // FileOutputStream outStream = new FileOutputStream("D://水印图.jpg");
        // IOUtils.copy(inputStream, outStream);
        // inputStream.close();
        // outStream.close();
    }
}
