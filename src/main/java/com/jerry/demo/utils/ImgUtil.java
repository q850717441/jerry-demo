package com.jerry.demo.utils;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * @author: Jerry
 * @create: 2020-07-21 13:48
 * @update: 2020-08-27
 * @description: 图片工具
 */
public class ImgUtil {
    /** 测试图片地址 */
    private static final String testImg = "/Users/jerry/Downloads/0b5a6229-3462-429a-a4b1-71e2f72d1757.jpg";
    /** 图片处理后地址 */
    private static final String resultImg = "/Users/jerry/Downloads/face_result.jpg";
    private static String resultImgUrl = "/Users/jerry/Files/oss/";


    /**
     * 实现斜水印铺满整张图
     */
    public static void main(String[] args) throws Exception {
        System.out.println("..添加水印图片开始...");
        //修改默认参数 设置水印间隔
//        ImgUtil.setImageMarkOptions(0.0f, 0, 20);
        String watermarkPath = "/Users/jerry/Downloads/logo.png";  //水印图片
        //水印图片地址 加水印图片地址 上传成功后文件地址
        ImgUtil.waterMarkByImg(watermarkPath, testImg, resultImg);
        System.out.println("..添加水印图片结束...");
    }

    /**
     * 使用 图像处理库 Sanselan 获取本地图片的信息
     *
     * */
    @Test
    public void getImgInfo2() throws IOException, ImageReadException {
        File picture = new File(testImg);
        // 判断文件是否存在
        System.out.println("判断文件是否存在=======================================");
        System.out.println(Imaging.hasImageFileExtension(picture));
        System.out.println("获得图片结构描述=======================================");
        // 获得图片结构描述
        System.out.println(Imaging.dumpImageFile(picture));
        System.out.println("获得图片信息=======================================");
        // 获得图片信息
        ImageInfo imageInfo = Imaging.getImageInfo(picture);
        System.out.println(imageInfo.getFormatName());
        System.out.println(imageInfo.getMimeType());
        System.out.println(imageInfo.getPhysicalHeightDpi());
        System.out.println(imageInfo.getPhysicalWidthDpi());
        System.out.println("获得图片尺寸=======================================");
        // 获得图片尺寸
        System.out.println(Imaging.getImageSize(picture));
        System.out.println("=======================================");
        System.out.println(Imaging.guessFormat(picture));
    }

    /**
     * 获取服务器上图片的尺寸
     *
     * @throws IOException
     */
    @Test
    public void getImgInfo1() throws IOException {
        InputStream murl = new URL("http://101.132.178.101:8762/image/20200603/b396bd07cf.jpg").openStream();
        BufferedImage image = ImageIO.read(murl);
        System.out.println(image.getWidth());   // 源图宽度
        System.out.println(image.getHeight());   // 源图高度
    }

    /** --------------------------------------------水印功能-------------------------------------------- */

    //定义图片水印字体类型
    public static final String FONT_NAME = "微软雅黑";

    //定义图片水印字体加粗、变细、倾斜等样式
    public static final int FONT_STYLE = Font.BOLD;

    //设置字体大小
    public static final int FONT_SIZE = 60;

    //设置文字透明程度
    public static float ALPHA = 0.9F;

//    public static void main(String[] args) {
//        markImageBySingleText(testImg, resultImgUrl, "testOne", "jpg", Color.blue, "Jerry", -45);
//        markImageByMoreText(testImg, resultImgUrl, "test2", "jpg", Color.CYAN, "Jerry", -45);
//    }

    /**
     * 给图片添加单个文字水印、可设置水印文字旋转角度
     * @param sourcePath 需要添加水印的图片路径（如：F:/images/6.jpg）
     * @param outputPath 添加水印后图片输出路径（如：F:/images/）
     * @param imageName 图片名称
     * @param imageType 图片类型
     * @param color 水印文字的颜色
     * @param word 水印文字
     * @param degree 水印文字旋转角度，为null表示不旋转
     * @return
     */
    public static Boolean markImageBySingleText(String sourcePath, String outputPath, String imageName, String imageType, Color color, String word, Integer degree) {
        try {
            //读取原图片信息
            File file = new File(sourcePath);
            if (!file.isFile()) {
                return false;
            }
            //获取源图像的宽度、高度
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建绘图工具对象
            Graphics2D graphics2D = bufferedImage.createGraphics();
            //其中的0代表和原图位置一样
            graphics2D.drawImage(image, 0, 0, width, height, null);
            //设置水印文字（设置水印字体样式、粗细、大小）
            graphics2D.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            //设置水印颜色
            graphics2D.setColor(color);
            //设置水印透明度
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
            //设置水印旋转
            if (null != degree) {
                graphics2D.rotate(Math.toRadians(degree), (double) bufferedImage.getWidth() / 2, (double) bufferedImage.getHeight() / 2);
            }
            int x = width - (FONT_SIZE * 4);
            int y = height / 2;
            //进行绘制
            graphics2D.drawString(word, x, y);
            graphics2D.dispose();
            //输出图片
            File sf = new File(outputPath, imageName + "." + imageType);
            // 保存图片
            ImageIO.write(bufferedImage, imageType, sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 给图片添加多个文字水印、可设置水印文字旋转角度
     * @param sourcePath 需要添加水印的图片路径
     * @param outputPath 添加水印后图片输出路径
     * @param imageName 图片名称
     * @param imageType 图片类型
     * @param color 水印文字的颜色
     * @param word 水印文字
     * @param degree 水印文字旋转角度，为null表示不旋转
     * @return
     */
    public static Boolean markImageByMoreText(String sourcePath, String outputPath, String imageName, String imageType, Color color, String word, Integer degree) {
        try {
            //读取原图片信息
            File file = new File(sourcePath);
            if (!file.isFile()) {
                return false;
            }
            //获取源图像的宽度、高度
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建绘图工具对象
            Graphics2D graphics2D = bufferedImage.createGraphics();
            //其中的0代表和原图位置一样
            graphics2D.drawImage(image, 0, 0, width, height, null);
            //设置水印文字（设置水印字体样式、粗细、大小）
            graphics2D.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            //设置水印颜色
            graphics2D.setColor(color);
            //设置水印透明度
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
            //设置水印旋转
            if (null != degree) {
                graphics2D.rotate(Math.toRadians(degree), (double) bufferedImage.getWidth() / 2, (double) bufferedImage.getHeight() / 2);
            }
            int x = width / 3;
            int y = FONT_SIZE;
            int space = height / FONT_SIZE;
            for (int i = 0; i < space; i++) {
                //如果最后一个坐标的y轴比height高，直接退出
                if ((y + FONT_SIZE) > height) {
                    break;
                }
                //进行绘制
                graphics2D.drawString(word, x, y);
                y += (2 * FONT_SIZE);
            }
            graphics2D.dispose();
            //输出图片
            File sf = new File(outputPath, imageName + "." + imageType);
            // 保存图片
            ImageIO.write(bufferedImage, imageType, sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     *  给图片添加单个图片水印、可设置水印图片旋转角度
     *icon 水印图片路径（如：F:/images/icon.png）
     *source 没有加水印的图片路径（如：F:/images/6.jpg）
     *output 加水印后的图片路径（如：F:/images/）
     *imageName 图片名称（如：11111）
     *imageType 图片类型（如：jpg）
     *degree 水印图片旋转角度，为null表示不旋转
     */
    public Boolean markImageBySingleIcon(String icon, String source, String output, String imageName, String imageType, Integer degree) {
        try {
            File file = new File(source);
            File ficon = new File(icon);
            if (!file.isFile()) {
                return false;
            }
            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            //icon高度
            int icheight = ic.getHeight(null);
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = (height / 2) - (icheight / 2);
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(icon);
            //得到Image对象。
            Image con = imgIcon.getImage();
            //透明度，最小值为0，最大值为1
            float clarity = 0.6f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
            //表示水印图片的坐标位置(x,y)
            //g.drawImage(con, 300, 220, null);
            g.drawImage(con, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 给图片不同位置添加多个图片水印、可设置水印图片旋转角度
     * icon 水印图片路径
     * source 没有加水印的图片路径
     * output 加水印后的图片路径
     * imageName 图片名称
     * imageType 图片类型
     * degree 水印图片旋转角度，为null表示不旋转
     */
    public Boolean markImageByMoreIcon(String icon, String source, String output, String imageName, String imageType, Integer degree) {
        try {
            File file = new File(source);
            File ficon = new File(icon);
            if (!file.isFile()) {
                return false;
            }
            //将icon加载到内存中
            Image ic = ImageIO.read(ficon);
            //icon高度
            int icheight = ic.getHeight(null);
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //图片宽
            int width = img.getWidth(null);
            //图片高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //创建一个指定 BufferedImage 的 Graphics2D 对象
            Graphics2D g = bi.createGraphics();
            //x,y轴默认是从0坐标开始
            int x = 0;
            int y = 0;
            //默认两张水印图片的间隔高度是水印图片的1/3
            int temp = icheight / 3;
            int space = 1;
            if (height >= icheight) {
                space = height / icheight;
                if (space >= 2) {
                    temp = y = icheight / 2;
                    if (space == 1 || space == 0) {
                        x = 0;
                        y = 0;
                    }
                }
            } else {
                x = 0;
                y = 0;
            }
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            for (int i = 0; i < space; i++) {
                if (null != degree) {
                    //设置水印旋转
                    g.rotate(Math.toRadians(degree), (double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
                }
                //水印图象的路径 水印一般为gif或者png的，这样可设置透明度
                ImageIcon imgIcon = new ImageIcon(icon);
                //得到Image对象。
                Image con = imgIcon.getImage();
                //透明度，最小值为0，最大值为1
                float clarity = 0.6f;
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, clarity));
                //表示水印图片的坐标位置(x,y)
                //g.drawImage(con, 300, 220, null);
                g.drawImage(con, x, y, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
                y += (icheight + temp);
            }
            g.dispose();
            File sf = new File(output, imageName + "." + imageType);
            ImageIO.write(bi, imageType, sf); // 保存图片
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /** --------------------------------------------实现斜水印铺满整张图-------------------------------------------- */
    /** 水印透明度 */
    private static float alpha = 0.5f;
    /** 水印图片旋转角度 */
    private static double degree = 0f;
    /** 水印图片间隔 */
    private static int interval = 0;
    /** 水印图片与源图得比例 */
    private static float proportion = 0.3f;

    /**
     * 设置水印参数，不设置就使用默认值
     * @param alpha 水印透明度
     * @param degree 水印图片旋转角度 *
     * @param interval 水印图片间隔
     */
    public static void setImageMarkOptions(float alpha, int degree, int interval, float proportion) {
        if (alpha != 0.0f) {
            ImgUtil.alpha = alpha;
        }
        if (degree != 0f) {
            ImgUtil.degree = degree;
        }
        if (interval != 0f) {
            ImgUtil.interval = interval;
        }
        if (proportion != 0f) {
            ImgUtil.proportion = proportion;
        }
    }

    /**
     * 给图片添加水印图片（保留源文件）
     * @param waterImgPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     */
    public static void waterMarkByImg(String waterImgPath, String srcImgPath, String targetPath) throws Exception {
        waterMarkByImg(waterImgPath, srcImgPath, targetPath, 0);
    }

    /**
     * 给图片添加水印图片(替换源文件)
     * @param waterImgPath 水印图片路径
     * @param srcImgPath 源图片路径
     */
    public static void waterMarkByImg(String waterImgPath, String srcImgPath) {
        try {
            waterMarkByImg(waterImgPath, srcImgPath, srcImgPath, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加水印图片、可设置水印图片旋转角度
     * @param waterImgPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     * @param degree 水印图片旋转角度
     */
    public static void waterMarkByImg(String waterImgPath, String srcImgPath, String targetPath, double degree) throws Exception {
        //判断目标路径是否存在，不存在则创建
        File uploadDirectory = new File(targetPath.substring(0, targetPath.lastIndexOf("/") + 1));
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            //如果父目录不存在，连同父目录一起创建。
            uploadDirectory.mkdirs();
        }

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 1、得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 2、设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 3、设置水印旋转
            if (0 != degree) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                                .getHeight() / 2);
            }
            // 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(waterImgPath);
            // 5、得到Image对象。
            Image img = imgIcon.getImage();
            // 根据图片大小设置水印大小
            int watermarkImageWidth = img.getWidth(null);
            int watermarkImageHeight = img.getHeight(null);
            int srcWidth = srcImg.getWidth(null);//源图得宽度
            double dmarkWidth = srcWidth * proportion;// 按图片宽度比例设置水印的宽度
            double dmarkHeight = dmarkWidth * ((double) watermarkImageHeight / (double) watermarkImageWidth);//强转为double计算宽高比例
            int imarkWidth = (int) dmarkWidth;
            int imarkHeight = (int) dmarkHeight;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 6、水印图片的位置
            for (int height = interval + imarkHeight; height < buffImg.getHeight(); height = height + interval + imarkHeight) {
                for (int weight = interval + imarkWidth; weight < buffImg.getWidth(); weight = weight + interval + imarkWidth) {
                    g.drawImage(img, weight - imarkWidth, height - imarkHeight, imarkWidth, imarkHeight, null);
                }
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            // 7、释放资源
            g.dispose();
            // 8、生成图片
            os = new FileOutputStream(targetPath);
            ImageIO.write(buffImg, "JPG", os);
            System.out.println("图片完成添加水印图片");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
