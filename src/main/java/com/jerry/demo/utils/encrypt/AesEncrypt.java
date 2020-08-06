package com.jerry.demo.utils.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * @author jerry
 * @create: 2020-08-06 10:21
 * @description: AES加密解密
 */
public class AesEncrypt {

    private static final String KEY = "Ser74rd6ghc4x9bt";//秘钥，必须16位
    private static final String OFFSET = "5C8q7hc9res00qes";//偏移量
    private static final String ENCODING = "UTF-8";//编码
    private static final String ALGORITHM = "AES";//算法
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认加密算法，CBC模式

    /**
     * AES加密
     * @param data /
     * @return String
     */
    public static String encrypt(String data) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("ASCII"),ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//CBC模式偏移量IV
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,iv);
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        return new Base64().encodeToString(encrypted);
    }

    /**
     * AES解密
     * @param data
     * @return String
     */
    public static String decrypt(String data) throws Exception{
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes("ASCII"),ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,iv);
        byte[] buffer = new Base64().decode(data);
        byte[] encrypted = cipher.doFinal(buffer);
        return new String(encrypted,ENCODING);
    }
}