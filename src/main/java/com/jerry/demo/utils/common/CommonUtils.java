package com.jerry.demo.utils.common;

import com.jerry.demo.utils.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: Jerry
 * @create: 2020-08-05 16:19
 * @description: 常用方法
 */
public class CommonUtils {

    /**
     * layUI 时间拆分
     * @param createTime 时间描述
     * @return List<String>
     */
    public static List<String> getStartAndEndTime(String createTime) {
        String startTime = "";
        String endTime = "";
        if (StringUtils.isNotBlank(createTime)) {
            startTime = createTime.substring(0, createTime.indexOf(" "));
            endTime = createTime.substring(createTime.lastIndexOf(" ") + 1);
        }
        List<String> timeList = new ArrayList<>();
        timeList.add(startTime);
        timeList.add(endTime);
        return timeList;
    }

    /**
     * 自动生成编号
     * 格式为：headStr + 日期（14位）+ 随机4位字符串
     * @param headStr 头类型
     * @return String
     */
    public static String getNewEquipmentNo(String headStr) {
        return headStr + DateUtils.getNowTimeStamp() + UUID.randomUUID().toString().replace("-", "").substring(0, 4);
    }

    /**
     * 生成32位编码
     * @return string
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
