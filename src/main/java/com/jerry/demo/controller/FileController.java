package com.jerry.demo.controller;

import com.jerry.demo.domain.EasyUiImg;
import com.jerry.demo.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author: Jerry
 * @create: 2020-08-06 16:57
 * @description: 文件接口
 */
@RestController
@RequestMapping("/")
public class FileController {

    @Resource
    private FileService fileService;

    @RequestMapping("/pic/upload")
    public EasyUiImg imgUpload(MultipartFile uploadFile) {
        return fileService.imgUpload(uploadFile);
    }
}
