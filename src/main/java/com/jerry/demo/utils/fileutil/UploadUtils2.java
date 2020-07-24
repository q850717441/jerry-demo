package com.jerry.demo.utils.fileutil;

import cn.hutool.core.io.FileUtil;
import com.jerry.demo.utils.common.DataResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Jerry
 * @create: 2020-07-20 14:40
 * @description: 文件上传2-随机生产10位文件名
 */
@RestController
@RequestMapping("/upload2")
public class UploadUtils2 {

    @PostMapping(value = "/add")
    public DataResult uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile file) {
        if (file == null) {
            return DataResult.fail("文件为空");
        }
        String filePath = "";
        try {
            String[] fileInfo = FileUploadUtil2.getFileInfo("/Users/jerry/Files/oss");
            System.out.println(file.getOriginalFilename());
            filePath = fileInfo[0] + "/" + fileInfo[1] + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            FileUploadUtil2.upload(request, response, file, fileInfo);
            return DataResult.success(filePath);
        } catch (Exception e) {
            FileUtil.del(filePath);
            throw e;
        }
    }


}
