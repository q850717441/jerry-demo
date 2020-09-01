package com.jerry.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author: Jerry
 * @create: 2020-08-10 18:00
 * @description: 实现对象(以及List集合) 和 JSON数据的转化
 * 1. private static final ObjectMapper MAPPER = new ObjectMapper();
 * 2. 将对象转换为JSON: MAPPER .writeValueAsString(对象\list)
 * 3. 将JSON还原对象 : MAPPER.readValue(json,对象.class\list.getclass)
 */
public class ObjectJsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //将对象转换为json
    public static String toJson(Object obj) {
        String json;
        try {
            json = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }

    //将json串转化为对象
    public static <T> T toObject(String json, Class<T> targetClass) {
        T obj;
        try {
            obj = MAPPER.readValue(json, targetClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return obj;
    }
}