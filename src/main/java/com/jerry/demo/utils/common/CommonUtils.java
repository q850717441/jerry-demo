package com.jerry.demo.utils.common;

import cn.hutool.core.util.StrUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jerry.demo.utils.date.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author: Jerry
 * @create: 2020-08-05 16:19
 * @description: 常用方法
 */
@Slf4j
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
     * 以UUID重命名
     *
     * @param fileName
     * @return
     */
    public static String renamePic(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return getUuid() + extName;
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
     * 随机6位数生成
     */
    public static String getRandomNum() {
        Random random = new Random();
        int num = random.nextInt(999999);
        //不足六位前面补0
        String str = String.format("%06d", num);
        return str;
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

    /**
     * 禁用词判断
     * @param param
     */
    public static void stopwords(String param) {
        if (StrUtil.isBlank(param)) {
            return;
        }
        // 转换成小写
        param = param.toLowerCase();
        // 判断是否包含非法字符
        for (String keyword : Constants.STOP_WORDS) {
            if (param.contains(keyword)) {
                log.error("名称包含禁用词：{}", keyword);
            }
        }
    }

}
