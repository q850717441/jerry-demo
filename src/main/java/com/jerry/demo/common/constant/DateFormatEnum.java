package com.jerry.demo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Jerry
 * @create: 2020-09-25 10:21
 * @update: 2020-09-25 10:21
 * @description: 时间格式
 */
@Getter
@AllArgsConstructor
public enum DateFormatEnum {
    /** 仅显示年月日，例如 20150811 */
    DATE_FORMAT1("yyyyMMdd"),
    /** 仅显示年月日，例如 2015-08-11 */
    DATE_FORMAT2("yyyy-MM-dd"),
    /** 仅显示年月日，例如 2015/08/11 */
    DATE_FORMAT3("yyyy/MM/dd"),
    /** 显示年月日时分秒，例如 2015-08-11 09:51:53. */
    DATETIME_FORMAT("yyyy-MM-dd HH:mm:ss"),
    /** 仅显示时分秒，例如 09:51:53. */
    TIME_FORMAT("HH:mm:ss"),

    ;

    private final String value;
}
