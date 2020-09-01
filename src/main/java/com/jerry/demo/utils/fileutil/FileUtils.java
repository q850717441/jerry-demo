package com.jerry.demo.utils.fileutil;

import com.jerry.demo.common.exception.BusinessException;
import com.jerry.demo.utils.date.DateUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Jerry
 * @create: 2020-07-29 14:37
 * @description: 文件工具
 */
public class FileUtils {

    /**
     * 文件上传工具
     * @param file 文件
     * @param type 储存文件类型目录名
     * @return 文件名、url、存储地址
     */
    public static Map<String, String> uploadUtil(MultipartFile file, String path, String type) {
        //存储文件夹
        String fileName = file.getOriginalFilename();
        String fileNameNew = UUID.randomUUID().toString().replace("-", "") + getFileType(fileName);
        String createTime = DateUtils.format(new Date(), "yyyyMMdd");
        String s = type + "/" + createTime + "/";
        String newPath = path + s;
        String newFilePathName = newPath + fileNameNew;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            //如果父目录不存在，连同父目录一起创建。
            uploadDirectory.mkdirs();
        }
        try {
            File newFile = new File(newFilePathName);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
        } catch (IOException e) {
            throw new BusinessException("上传文件失败");
        }
        Map<String, String> resultMap = new HashMap<>();
        //文件名
        resultMap.put("fileName", fileName);
        //服务器存储路径
        resultMap.put("realPath", newFilePathName);
        //回显路径
        //数据库存储路径、相对路径
        return resultMap;
    }

    /**
     * 下载DB文件
     * @param request /
     * @param response /
     * @param file /
     * @param deleteOnExit 在JVM进程退出的时候删除文件,通常用在临时文件的删除.
     */
    public static void downloadLocal(HttpServletRequest request, HttpServletResponse response, File file, boolean deleteOnExit, String fileName) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (deleteOnExit) {
                        file.deleteOnExit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getFileType(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

}
