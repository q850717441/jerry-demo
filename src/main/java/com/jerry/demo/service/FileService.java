package com.jerry.demo.service;

import com.jerry.demo.utils.common.DataResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Jerry
 * @create: 2020-08-06 16:59
 * @description: 文件
 */
public interface FileService {

    /**
     * 图片上传-方案1
     * @param uploadFile 图片文件
     * @return
     */
    DataResult imgUpload(MultipartFile uploadFile);

}
