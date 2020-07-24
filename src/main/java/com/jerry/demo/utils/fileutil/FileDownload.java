package com.jerry.demo.utils.fileutil;

import cn.hutool.core.util.ZipUtil;
import com.jerry.demo.utils.common.DataResult;
import org.apache.poi.util.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: Jerry
 * @create: 2020-07-21 16:06
 * @description: 文件下载
 */
@RestController
@RequestMapping("/download")
public class FileDownload {
    @GetMapping("/import")
    public DataResult download(HttpServletRequest request, HttpServletResponse response) {
        File file = new File("/Users/jerry/Files/oss");
        String zipPath = file.getPath() + ".zip";
        ZipUtil.zip(file.getPath(), zipPath);
        downloadFile(request, response, new File(zipPath), true);
        return DataResult.success();
    }

    /**
     * 下载文件
     * @param request /
     * @param response /
     * @param file /
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file, boolean deleteOnExit) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
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
}
