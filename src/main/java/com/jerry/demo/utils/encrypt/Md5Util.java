package com.jerry.demo.utils.encrypt;

import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jerry
 * @create: 2020-09-01 10:21
 * @description: Md5Util
 */
public class Md5Util {
    /**
     * base64加密
     */
    public static String md5String(String oldPwd) {
        try {
            //实现Base64的编码
            BASE64Encoder base64 = new BASE64Encoder();
            //进行加密
            return base64.encode(oldPwd.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5加密
     */
    public static String stringToMd5(String plainText) {
        byte[] secretBytes;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }

    public static void main(String[] args) {
        System.out.println(stringToMd5("123456"));
    }
}