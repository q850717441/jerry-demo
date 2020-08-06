package com.jerry.demo.service.impl;

import com.jerry.demo.domain.EasyUiImg;
import com.jerry.demo.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author jerry
 */
@Service
public class FileServiceImpl implements FileService {
    private String localDir = "/Users/jerry/Downloads/";

    /**
     *1.判断文件是否为图片
     *2.防止恶意程序
     *3.图片分文件保存
     *4.防止重名
     */
    @Override
    public EasyUiImg imgUpload(MultipartFile uploadFile) {
        EasyUiImg easyUIImg = new EasyUiImg();
        //1.判断文件是否为图片类型   abc.jpg
        String fileName = uploadFile.getOriginalFilename();
        //将字符串转化为小写
        fileName = fileName.toLowerCase();
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
            //表示不满足规则
            return EasyUiImg.fail();
        }
        try {
            //2.判断是否为恶意程序 转化为图片对象
            BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if (width == 0 || height == 0) {
                return EasyUiImg.fail();
            }
            //3.实现分文件存储  按照yyyy/MM/dd/
            String dateDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            //生成文件目录    D://image/yyyy/MM/dd
            String fileDirPath = localDir + dateDir;
            File dirFile = new File(fileDirPath);
            //如果没有目录,则创建目录
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            //4.生成文件名称防止重名  name.type
            int index = fileName.lastIndexOf(".");
            //.jpg
            String fileType = fileName.substring(index);
            String uuid = UUID.randomUUID().toString();
            //拼接文件名称
            String realFileName = uuid + fileType;
            //5.实现文件上传
            uploadFile.transferTo(new File(fileDirPath + realFileName));
            //暂时使用网络地址代替真是url地址.
            easyUIImg.setWidth(width)
                    .setHeight(height)
                    .setUrl("https://img14.360buyimg.com/n0/jfs/t1/81541/28/10612/304749/5d7f5f1dE03dfb7e1/c23ead253c54954a.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            return EasyUiImg.fail();
        }
        return easyUIImg;
    }
}