package com.szhengzhu.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片工具类， 图片水印，文字水印，缩放，补白等
 */
public final class ImageUtils {

	private ImageUtils() {}

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
	public static BufferedImage pressImage(String targetImg, String waterImg,
			int x, int y, float alpha) {
		File file = new File(targetImg);
		return pressImage(file, waterImg, x, y, alpha);
	}

	public static BufferedImage pressImage(File file, String waterImg, int x,
			int y, float alpha) {
		try {
			BufferedImage image = ImageIO.read(file);
			return pressImage(image, waterImg, x, y, alpha);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage pressImage(BufferedImage image,
			String waterImg, int x, int y, float alpha) {
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
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
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
	public static BufferedImage pressText(String targetImg, String pressText,
			String fontName, int fontStyle, int fontSize, Color color, int x,
			int y, float alpha) {
		File file = new File(targetImg);
		return pressText(file, pressText, fontName, fontStyle, fontSize, color,
				x, y, alpha);
	}

	public static BufferedImage pressText(File file, String pressText,
			String fontName, int fontStyle, int fontSize, Color color, int x,
			int y, float alpha) {
		try {
			BufferedImage image = ImageIO.read(file);
			return pressText(image, pressText, fontName, fontStyle, fontSize,
					color, x, y, alpha);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static BufferedImage pressText(BufferedImage image,
			String pressText, String fontName, int fontStyle, int fontSize,
			Color color, int x, int y, float alpha) {
		try {
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
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
	public static BufferedImage resize(String filePath, int width, int height,
			boolean bb) {
		File filse = new File(filePath);
		return resize(filse, width, height, bb);
	}

	public static BufferedImage resize(File file, int width, int height,
			boolean bb) {
		try {
			BufferedImage bi = ImageIO.read(file);
			return resize(bi, width, height, bb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage resize(BufferedImage bi, int width, int height,
			boolean bb) {
		double ratio = 0; // 缩放比例
		Image itemp = bi.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
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
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(1, 1), null);
			itemp = op.filter(bi, null);
			return (BufferedImage) itemp;
		}
		if (bb) {
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = image.createGraphics();
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, width, height);
			if (width == itemp.getWidth(null))
				graphics.drawImage(itemp, 0,
						(height - itemp.getHeight(null)) / 2,
						itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
			else
				graphics.drawImage(itemp, (width - itemp.getWidth(null)) / 2,
						0, itemp.getWidth(null), itemp.getHeight(null),
						Color.white, null);
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

	// public static BufferedImage createOrderImage(OrderInfo order) {
	// try {
	// InputStream imagein = new FileInputStream(File.separator + "usr"
	// + File.separator + "javaApplication" + File.separator
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

	// private static int drawBaseInfo(OrderInfo o, Graphics2D g, Font f,
	// int left, int start, int width) {
	// g.drawString("地址：" + o.getUser_area(), left, start);
	// List<String> rows = getStringRows(g, f, o.getUser_address(), width);
	// for (int i = 0, len = rows.size(); i < len; i++)
	// g.drawString(rows.get(i), left, start += 36);
	// g.drawString("联系人：" + o.getUser_name(), left, start += 42);
	// g.drawString("联系电话：" + o.getUser_phone(), left, start += 42);
	// g.drawString("订单详情：", left, start += 42);
	// return start;
	// }

	// private static int drawOrderInfo(List<OrderItem> list, Graphics g,
	// int left, int start) {
	// String t;
	// for (OrderItem o : list) {
	// t = o.getGoods_name();
	// t = t.length() <= 15 ? t : t.substring(0, 15);
	// g.drawString(t + "*" + o.getQuantity(), left, start += 36);
	// }
	// return start;
	// }

	public static BufferedImage mergeImage(BufferedImage background,
			BufferedImage QRcode) {
		int width = background.getWidth(null);
		int height = background.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
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

	// private static String getStringRow(Graphics g, FontMetrics fm, String
	// text,
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

}