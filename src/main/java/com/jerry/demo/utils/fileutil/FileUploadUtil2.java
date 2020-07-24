package com.jerry.demo.utils.fileutil;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUploadUtil2 {
    //fileInfo[0]存放文件存放的文件夹，fileInfo[1]存放新生成的随机文件名
    public static void upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String[] fileInfo){
        try {
            response.setContentType("application/json;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileInfo[0].getBytes("utf-8"), "ISO8859-1"));
            bis = new BufferedInputStream(file.getInputStream());
            new File(fileInfo[0]).mkdirs();
            String filePath = fileInfo[0]+"/"+fileInfo[1]+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取由UUID组成的文件路径
    public static String[] getFileInfo(String path){
        String[] strs = new String[3];
        StringBuffer sb = new StringBuffer(path);
        //建立年月日文件夹
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();
        sb.append("/").append(sdf.format(d));
        strs[0] = sb.toString();
        //获取随机文件名
        String filename = UUID.randomUUID().toString().replace("-","").substring(0,10);
        sb.append("/").append(filename);
        strs[1] = filename;
        strs[2] = sb.toString();
        return strs;
    }
}
