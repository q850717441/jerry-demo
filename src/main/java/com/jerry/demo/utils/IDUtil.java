package com.jerry.demo.utils;

import com.jerry.demo.utils.date.DateUtils;

import java.util.Random;
import java.util.UUID;

/**
 * @author: Jerry
 * @create: 2020-08-27 15:44
 * @update: 2020-08-27
 * @description: ID生成策略
 */
public class IDUtil {
    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        return new Long(str);
    }


    /**
     * 自定义头部 生成编号
     * 格式为：headStr + 日期（14位）+ 随机4位字符串
     * @param headStr 头类型
     * @return String
     */
    public static String getNewEquipmentNo(String headStr) {
        return headStr + DateUtils.getNowTimeStamp() + UUID.randomUUID().toString().replace("-", "").substring(0, 4);
    }
}
