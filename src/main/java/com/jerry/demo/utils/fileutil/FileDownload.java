package com.jerry.demo.utils.fileutil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author: Jerry
 * @create: 2020-07-21 16:06
 * @description: 下载文件
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class FileDownload {
    private static final String filePath = "http://123.56.98.7:8889/2016690-20200717105942214-957138008.png";


    @GetMapping("/import")
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //压缩包
//        File file = new File("/Users/jerry/Files/oss");
//        String zipPath = file.getPath() + ".zip";
//        ZipUtil.zip(file.getPath(), zipPath);

        //下载本地文件
        File file = new File("/Users/jerry/Files/oss/testImg.jpg");
        downloadLocal(request, response, file, false);

        //下载网络文件
//        String s = downloadFile(filePath, "/Users/jerry/Downloads/test/");
    }

    /**
     * 下载DB文件
     * @param request /
     * @param response /
     * @param file /
     * @param deleteOnExit 在JVM进程退出的时候删除文件,通常用在临时文件的删除.(删除原文件）
     */
    public static void downloadLocal(HttpServletRequest request, HttpServletResponse response, File file, boolean deleteOnExit) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
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
     * 支持在线打开的方式
     * @param filePath /
     * @param response /
     * @param isOnLine /
     * @throws Exception
     */
    public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) throws Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        br.close();
        out.close();
    }


    /**
     * FileUtils 下载网络文件
     *
     * @param serverUrl 网络文件地址
     * @param savePath 本地保存路径
     * @return
     */
    public static String downloadFile(String serverUrl, String savePath) throws Exception {
        String result;
        File f = new File(savePath);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                throw new Exception("makdirs: '" + savePath + "'fail");
            }
        }
        String suffix = serverUrl.substring(serverUrl.lastIndexOf("."));
        String filePath = savePath + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + suffix;
        URL url = new URL(serverUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而放回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
        long totalSize = Long.parseLong(conn.getHeaderField("Content-Length"));
        if (totalSize > 0) {
            FileUtils.copyURLToFile(url, new File(filePath));
        } else {
            throw new Exception("can not find serverUrl :{}" + serverUrl);
        }
        return filePath;
    }

}
