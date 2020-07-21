package com.jerry.demo.utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author: Jerry
 * @create: 2020-07-21 13:48
 * @description: 图片工具
 */
public class ImgUtil {
    /**
     * 本地获取图片的大小和尺寸
     * */
    @Test
    public void testImg2() throws IOException {
        File picture = new File("/Users/jerry/Files/oss/0b5a6229-3462-429a-a4b1-71e2f72d1757.jpg");
        BufferedImage image = ImageIO.read(new FileInputStream(picture));
        System.out.println(String.format("%.1f", picture.length() / 1024.0));// 源图大小
        System.out.println(image.getWidth()); // 源图宽度
        System.out.println(image.getHeight()); // 源图高度
    }

    /**
     * 获取服务器上图片的尺寸
     *
     * @throws IOException
     */
    @Test
    public void testImg1() throws IOException {
        InputStream murl = new URL("http://101.132.178.101:8762/image/20200603/b396bd07cf.jpg").openStream();
        BufferedImage image = ImageIO.read(murl);
        System.out.println(image.getWidth());   // 源图宽度
        System.out.println(image.getHeight());   // 源图高度
    }

}
