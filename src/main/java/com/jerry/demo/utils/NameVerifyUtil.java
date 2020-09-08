package com.jerry.demo.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Jerry
 * @create: 2020-09-07 14:30
 * @update: 2020-09-07
 * @description: 用户名验证工具类
 */
@Slf4j
public class NameVerifyUtil {

    public static final String regUsername = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{1,16}$";

    /**
     * 由字母、数字、下划线、中文组成，不能超过16位
     */
    private static final Pattern pUsername = Pattern.compile(regUsername);

    public static boolean username(String v) {

        if (StrUtil.isBlank(v)) {
            return false;
        }
        Matcher m = pUsername.matcher(v);
        return m.matches();
    }


}
