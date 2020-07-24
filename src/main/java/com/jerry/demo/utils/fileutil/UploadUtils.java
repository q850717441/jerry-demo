package com.jerry.demo.utils.fileutil;

import com.alibaba.excel.util.DateUtils;
import com.jerry.demo.config.FileUploadProperties;
import com.jerry.demo.utils.common.DataResult;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jerry
 * @create: 2020-07-20 14:40
 * @description: 文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadUtils {

    @Resource
    private FileUploadProperties fileUploadProperties;

    @Resource
    private Environment environment;

    /**
     * 测试图片上传后解析
     */
//    @PostMapping("/import")
//    public DataResult testImageUpload(@RequestParam("file") MultipartFile file) throws IOException, ImageReadException {
//        String property = environment.getProperty("file.path");
//        String s = uploadUtil(file, property);
//        File picture = new File(s);
//        ImageInfo imageInfo = Imaging.getImageInfo(picture);
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("src", s);
//        resultMap.put("Physical Height Dpi", imageInfo.getPhysicalHeightDpi());
//        resultMap.put("Physical Width Dpi", imageInfo.getPhysicalWidthDpi());
//        return DataResult.success(resultMap);
//    }

    //    @PostMapping(value = "/import", headers = "content-type=multipart/*")
    @PostMapping("/import")
    public DataResult importSqlLite(@RequestParam("file") MultipartFile file) throws IOException {
        //判断文件是否空
        if (file == null || file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename().trim())) {
            return DataResult.fail("文件为空");
        }
        String path = uploadUtil(file, fileUploadProperties.getPath());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("src", path);
        return DataResult.success(resultMap);
    }

    public static String uploadUtil(MultipartFile file, String path) throws IOException {
        //存储文件夹
        String createTime = DateUtils.format(new Date(), "yyyyMMdd");
        String newPath = path + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        File newFile = new File(newPath + file.getOriginalFilename());
        file.transferTo(newFile);
        return newFile.getPath();
    }

}