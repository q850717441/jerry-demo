package com.jerry.demo.utils.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jerry.demo.utils.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author: Jerry
 * @create: 2020-08-05 16:19
 * @description: 常用方法
 */
public class CommonUtils {

    /**
     * layUI 时间拆分:开始与结束
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
    public static String getUuid() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    /**
     * 线程池工具类
     *
     * @param threadPoolName  线程池名称
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param queueSize       队列容量
     * @return ExecutorService
     */
    public static ExecutorService getExecutorService(String threadPoolName, int corePoolSize, int maximumPoolSize, int queueSize) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(threadPoolName + "-%d").build();
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

}
