package com.jerry.demo.utils.fileupload;

import com.alibaba.excel.util.DateUtils;
import com.jerry.demo.config.FileUploadProperties;
import com.jerry.demo.utils.common.DataResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author: Jerry
 * @create: 2020-07-20 14:40
 * @description: 文件上传-解压
 */
@RestController
@RequestMapping("/upload")
public class UploadUtils {

    @Resource
    private FileUploadProperties fileUploadProperties;

    //    @PostMapping(value = "/import", headers = "content-type=multipart/*")
    @PostMapping("/import")
    public DataResult importSqlLite(@RequestParam("file") MultipartFile file) throws IOException {
        DataResult result = DataResult.success();
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        //存储文件夹
        String createTime = DateUtils.format(new Date(), "yyyyMMdd");
        String newPath = fileUploadProperties.getPath() + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        File newFile = new File(newPath + file.getOriginalFilename());
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("src", newFile.getPath());
        return DataResult.success(resultMap);
    }


    //文件路径
    private String dest = "/Users/jerry/Downloads/测试图片批量上传.zip";
    //解压后图片保存的路径
    private String picPath = "/Users/jerry/Files/oss/unzip/";

    /**
     * 使用ZipFile解压文件
     */
    public void unzip() {
        //targetPath输出文件路径
        File targetFile = new File(picPath);
        // 如果目录不存在，则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //sourcePath压缩包文件路径
        try (ZipFile zipFile = new ZipFile(new File(dest))) {
            System.out.println("file nums:" + zipFile.size());
            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                //依次获取压缩包内的文件实体对象
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                System.out.println("this file size:" + entry.getSize());
                String name = entry.getName();
                if (entry.isDirectory()) {
                    continue;
                }
                try (BufferedInputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry))) {
                    // 需要判断文件所在的目录是否存在，处理压缩包里面有文件夹的情况
                    String outName = picPath + "/" + name;
                    File outFile = new File(outName);
                    File tempFile = new File(outName.substring(0, outName.lastIndexOf("/")));
                    if (!tempFile.exists()) {
                        tempFile.mkdirs();
                    }
                    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outFile))) {
                        int len;
                        byte[] buffer = new byte[1024];
                        while ((len = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, len);
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}