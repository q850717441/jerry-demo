package com.jerry.demo.utils.hutool.core;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: Jerry
 * @create: 2020-07-21 11:34
 * @description: 图片工具:针对awt中图片处理进行封装，这些封装包括：缩放、裁剪、转为黑白、加水印等操作。
 */
public class ImgUtilTest {
    /** 测试图片地址 */
    private String testImg = "/Users/jerry/Files/oss/20200721/0b5a6229-3462-429a-a4b1-71e2f72d1757.jpg";
    /** 图片处理后地址 */
    private String resultImg = "/Users/jerry/Files/oss/20200721/face_result.jpg";

    /**
     * scale 缩放图片
     * 提供两种重载方法：其中一个是按照长宽缩放，另一种是按照比例缩放。
     */
    @Test
    public void scale() {
        ImgUtil.scale(
                FileUtil.file(testImg),
                FileUtil.file(resultImg),
                0.5f//缩放比例
        );
    }

    /**
     * cut 剪裁图片
     */
    @Test
    public void cut() {
        ImgUtil.cut(
                FileUtil.file(testImg),
                FileUtil.file(resultImg),
                new Rectangle(200, 200, 300, 1000)//裁剪的矩形区域
        );
    }

    /**
     * slice 按照行列剪裁切片（将图片分为20行和20列）
     */
    @Test
    public void slice() {
        ImgUtil.slice(FileUtil.file(testImg), FileUtil.file(resultImg), 10, 10);
    }

    /**
     * convert 图片类型转换，支持GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG等
     */
    @Test
    public void convert() {
        ImgUtil.convert(FileUtil.file(testImg), FileUtil.file(resultImg));
    }

    /**
     * gray 彩色转为黑白
     */
    @Test
    public void gray() {
        ImgUtil.gray(FileUtil.file(testImg), FileUtil.file(resultImg));
    }

    /**
     * pressText 添加文字水印
     */
    @Test
    public void pressText() {
        ImgUtil.pressText(
                FileUtil.file(testImg),
                FileUtil.file(resultImg),
                "版权所有", Color.WHITE, //文字
                new Font("黑体", Font.BOLD, 100), //字体
                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
        );
    }

    /**
     * pressImage 添加图片水印
     */
    @Test
    public void pressImage() {
        ImgUtil.pressImage(
                FileUtil.file(testImg),
                FileUtil.file(resultImg),
                ImgUtil.read(FileUtil.file("d:/picTest/1432613.jpg")), //水印图片
                0, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                0, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                0.1f
        );
    }

    /**
     * rotate 旋转图片
     */
    @Test
    public void rotate() {
        // 旋转180度
        try {
            BufferedImage image = (BufferedImage) ImgUtil.rotate(ImageIO.read(FileUtil.file(testImg)), 180);
            ImgUtil.write(image, FileUtil.file(resultImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * flip 水平翻转图片
     */
    @Test
    public void flip() {
        ImgUtil.flip(FileUtil.file(testImg), FileUtil.file(resultImg));
    }

}
