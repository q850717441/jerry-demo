package com.jerry.demo.utils.hutool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import org.junit.Test;

import java.io.File;

/**
 * @author: Jerry
 * @create: 2020-07-21 12:25
 * @description: ZipUtil就是针对java.util.zip做工具化封装，使压缩解压操作可以一个方法搞定，并且自动处理文件和目录的问题，
 *  不再需要用户判断，压缩后的文件也会自动创建文件，自动创建父目录，大大简化的压缩解压的复杂度。
 */
public class ZipUtilTest {

    /**
     * 压缩ZipUtil.zip 方法提供一系列的重载方法，满足不同需求的压缩需求
     * 第一种：将aaa目录下的所有文件目录打包到d:/aaa.zip
     */
    @Test
    public void zip1() {
//        第一种：将aaa目录下的所有文件目录打包到d:/aaa.zip
        File zip1 = ZipUtil.zip("/Users/jerry/Files/oss/20200721");
        System.out.println(zip1.getPath());
    }

    /**
     * 第二种：指定打包后保存的目的地，自动判断目标是文件还是文件夹
     */
    @Test
    public void zip2() {
        //将aaa目录下的所有文件目录打包到d:/bbb/目录下的aaa.zip文件中
        ZipUtil.zip("d:/aaa", "d:/bbb/");
        //将aaa目录下的所有文件目录打包到d:/bbb/目录下的ccc.zip文件中
        ZipUtil.zip("d:/aaa", "d:/bbb/ccc.zip");
    }

    /**
     * 第三种：可选是否包含被打包的目录。比如我们打包一个照片的目录，打开这个压缩包有可能是带目录的，也有可能是打开压缩包直接看到的是文件。
     * zip方法增加一个boolean参数可选这两种模式，以应对众多需求。
     */
    @Test
    public void zip3() {
        //将aaa目录以及其目录下的所有文件目录打包到d:/bbb/目录下的ccc.zip文件中
        ZipUtil.zip("/Users/jerry/Files/oss/20200721", "/Users/jerry/Files/oss/ccc.zip", true);
    }

    /**
     * 第四种：多文件或目录压缩。可以选择多个文件或目录一起打成zip包。
     */
    @Test
    public void zip4() {
        ZipUtil.zip(FileUtil.file("d:/bbb/ccc.zip"), false,
                FileUtil.file("d:/test1/file1.txt"),
                FileUtil.file("d:/test1/file2.txt"),
                FileUtil.file("d:/test2/file1.txt"),
                FileUtil.file("d:/test2/file2.txt")
        );
    }

    /**
     * 解压ZipUtil.unzip 解压。同样提供几个重载，满足不同需求。
     */
    @Test
    public void unzip() {
        File unzip = ZipUtil.unzip("/Users/jerry/Files/oss/测试图片批量上传.zip", "/Users/jerry/Files/oss/20200721");
        System.out.println(unzip.getPath());
    }
}
