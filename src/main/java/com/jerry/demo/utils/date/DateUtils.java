package com.jerry.demo.utils.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: Jerry
 * @create: 2020-05-12
 * @update: 2020-08-05
 * @description: 时间工具-old
 */
public class DateUtils {
    /** 时间格式(yyyyMMdd) */
    public final static String DATEPATTERN = "yyyyMMdd";

    /**
     * 仅显示年月日，例如 2015-08-11.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 显示年月日时分秒，例如 2015-08-11 09:51:53.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 仅显示时分秒，例如 09:51:53.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 每天的毫秒数 8640000.
     */
    public static final long MILLISECONDS_PER_DAY = 86400000L;

    /**
     * 每周的天数.
     */
    public static final long DAYS_PER_WEEK = 7L;

    /**
     * 每小时毫秒数.
     */
    public static final long MILLISECONDS_PER_HOUR = 3600000L;

    /**
     * 每分钟秒数.
     */
    public static final long SECONDS_PER_MINUTE = 60L;

    /**
     * 每小时秒数.
     */
    public static final long SECONDS_PER_HOUR = 3600L;

    /**
     * 每天秒数.
     */
    public static final long SECONDS_PER_DAY = 86400L;

    /**
     * 每个月秒数，默认每月30天.
     */
    public static final long SECONDS_PER_MONTH = 2592000L;

    /**
     * 每年秒数，默认每年365天.
     */
    public static final long SECONDS_PER_YEAR = 31536000L;

    /**
     * 常用的时间格式.
     */
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    /**
     * 将Date以pattern格式为字符串
     * @param date Date
     * @param pattern 格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }


    /**
     * 生成当前时间戳
     * @return String
     */
    public static String getNowTimeStamp() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * 将 字符串 转换为 日期格式.
     * 支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"
     * @param str 日期格式的字符串
     * @return Date
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(str.toString(), parsePatterns);
        } catch (java.text.ParseException e) {
            return null;
        }
    }

    /**
     * 将TimeStamp转换为String
     * @param timestamp Timestamp
     * @return String
     */
    public static String getStringByTS(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    }

    /**
     * 将String转换为TimeStamp
     * @param str String
     * @return Timestamp
     */
    public static Timestamp getTimestampByString(String str) {
        return Timestamp.valueOf(str);
    }

    /**
     * 将 字符串 转 Time类型(时、分、秒)
     * @param timeStr "12:00:00"
     * @return java.sql.Time
     * @throws ParseException
     */
    public static Time stringToTimeUtil(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");//HH为24小时制,hh为12小时制
        try {
            Time time = new Time(simpleDateFormat.parse(timeStr).getTime());
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到当前日期字符串.
     * @return String 日期字符串，例如2015-08-11
     */
    public static String getDate() {
        return getDate(DateUtils.DATE_FORMAT);
    }

    /**
     * 得到当前时间字符串.
     * @return String 时间字符串，例如 09:51:53
     */
    public static String getTime() {
        return formatDate(new Date(), DateUtils.TIME_FORMAT);
    }

    /**
     * 得到当前日期和时间字符串.
     * @return String 日期和时间字符串，例如 2015-08-11 09:51:53
     * @since 1.0
     */
    public static String getDateTime() {
        return formatDate(new Date(), DateUtils.DATETIME_FORMAT);
    }

    /**
     * 获取当前时间指定格式下的字符串.
     * @param pattern
     *            转化后时间展示的格式，例如"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @return String 格式转换之后的时间字符串.
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 获取指定日期的字符串格式.
     * @param date  需要格式化的时间，不能为空
     * @param pattern 时间格式，例如"yyyy-MM-dd"，"yyyy-MM-dd HH:mm:ss"等
     * @return String 格式转换之后的时间字符串.
     */
    public static String getDate(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 获取日期时间字符串，默认格式为（yyyy-MM-dd）.
     * @param date 需要转化的日期时间
     * @param pattern 时间格式，例如"yyyy-MM-dd" "HH:mm:ss" "E"等
     * @return String 格式转换后的时间字符串
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, DateUtils.DATE_FORMAT);
        }
        return formatDate;
    }

    /**
     * 获取当前年份字符串.
     * @return String 当前年份字符串，例如 2015
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取当前月份字符串.
     * @return String 当前月份字符串，例如 08
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 获取当前天数字符串.
     * @return String 当前天数字符串，例如 11
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 获取当前星期字符串.
     * @return String 当前星期字符串，例如星期二
     */
    public static String getWeekOfNow() {
        return formatDate(new Date(), "E");
    }


    /**
     * 获取当前日期与指定日期相隔的天数.
     * @param date 给定的日期
     * @return long 日期间隔天数，正数表示给定日期在当前日期之前，负数表示在当前日期之后
     */
    public static long pastDays(Date date) {
        // 将指定日期转换为yyyy-MM-dd格式
        date = DateUtils.parseDate(DateUtils.formatDate(date, DateUtils.DATE_FORMAT));
        // 当前日期转换为yyyy-MM-dd格式
        Date currentDate = DateUtils.parseDate(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT));
        long t = 0;
        if (date != null && currentDate != null) {
            t = (currentDate.getTime() - date.getTime()) / DateUtils.MILLISECONDS_PER_DAY;
        }
        return t;
    }

    /**
     * 获取当前日期指定天数之后的日期.
     * @param num   相隔天数
     * @return Date 日期
     */
    public static Date nextDay(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) + num);
        return curr.getTime();
    }

    /**
     * 获取当前日期指定月数之后的日期.
     * @param num   间隔月数
     * @return Date 日期
     */
    public static Date nextMonth(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + num);
        return curr.getTime();
    }

    /**
     * 获取某个日期所处月份的天数
     * @param date 日期
     * @return 天数
     */
    public static int getMonthDays(Date date) {
        Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 将某个时间所处月份的每天，用于图标展示查月份数据x轴
     * @param date
     * @return
     */
    public static List<String> monthDayString(Date date) {
        List<String> days = new ArrayList<>();
        int monthDays = getMonthDays(date);
        for (int i = 1; i <= monthDays; i++) {
            if (i < 10) {
                days.add("0" + i);
            } else {
                days.add(i + "");
            }
        }

        return days;
    }

    /**
     * 获取当前日期指定年数之后的日期.
     * @param num    间隔年数
     * @return Date 日期
     */
    public static Date nextYear(int num) {
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + num);
        return curr.getTime();
    }

    /**
     * 将 Date 日期转化为 Calendar 类型日期.
     * @param date   给定的时间，若为null，则默认为当前时间
     * @return Calendar Calendar对象
     * @since 1.0
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        // calendar.setFirstDayOfWeek(Calendar.SUNDAY);//每周从周日开始
        // calendar.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
        if (date != null) {
            calendar.setTime(date);
        }
        return calendar;
    }

    /**
     * 计算两个日期之间相差天数.
     * @param start     计算开始日期
     * @param end       计算结束日期
     * @return long 相隔天数
     */
    public static long getDaysBetween(Date start, Date end) {
        // 将指定日期转换为yyyy-MM-dd格式
        start = DateUtils.parseDate(DateUtils.formatDate(start, DateUtils.DATE_FORMAT));
        // 当前日期转换为yyyy-MM-dd格式
        end = DateUtils.parseDate(DateUtils.formatDate(end, DateUtils.DATE_FORMAT));

        long diff = 0;
        if (start != null && end != null) {
            diff = (end.getTime() - start.getTime()) / DateUtils.MILLISECONDS_PER_DAY;
        }
        return diff;
    }

    /**
     * 计算两个日期之前相隔多少周.
     * @param start      计算开始时间
     * @param end    计算结束时间
     * @return long 相隔周数，向下取整
     */
    public static long getWeeksBetween(Date start, Date end) {
        return getDaysBetween(start, end) / DateUtils.DAYS_PER_WEEK;
    }

    /**
     * 获取与指定日期间隔给定天数的日期.
     * @param specifiedDay    给定的字符串格式日期，支持的日期字符串格式包括"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss",
     *            "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
     *            "yyyy/MM/dd HH:mm"
     * @param num   间隔天数
     * @return String 间隔指定天数之后的日期
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int num) {
        Date specifiedDate = parseDate(specifiedDay);
        Calendar c = Calendar.getInstance();
        c.setTime(specifiedDate);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);
        String dayAfter = formatDate(c.getTime(), DateUtils.DATE_FORMAT);
        return dayAfter;
    }

    /**
     * 计算两个日期之前间隔的小时数.
     * @param date1 结束时间
     * @param date2 开始时间
     * @return String 相差的小时数，保留一位小数
     */
    public static String dateMinus(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return "0";
        }
        Long r = date1.getTime() - date2.getTime();
        DecimalFormat df = new DecimalFormat("#.0");
        double result = r * 1.0 / DateUtils.MILLISECONDS_PER_HOUR;
        return df.format(result);
    }

    /**
     * 计算两个日期之前间隔的差值(秒)
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return String 相差的秒数
     */
    public static String printDifference(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();
        different /= 1000;
        return different >= 0 ? String.valueOf(different) : "0";
    }

    /**
     * 获取当前季度 .
     * @return Integer 当前季度数
     */
    public static Integer getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        Integer month = calendar.get(Calendar.MONTH) + 1;
        int season = 0;
        if (month >= 1 && month <= 3) {
            season = 1;
        } else if (month >= 4 && month <= 6) {
            season = 2;
        } else if (month >= 7 && month <= 9) {
            season = 3;
        } else if (month >= 10 && month <= 12) {
            season = 4;
        }
        return season;
    }

    /**
     * 相当于目前多久之前
     * @param seconds 秒数
     * @return String 例如 16分钟前、2小时前、3天前、4月前、5年前等
     */
    public static String getIntervalBySeconds(long seconds) {
        StringBuffer buffer = new StringBuffer();
        if (seconds < SECONDS_PER_MINUTE) {
            buffer.append(seconds).append("秒前");
        } else if (seconds < SECONDS_PER_HOUR) {
            buffer.append(seconds / SECONDS_PER_MINUTE).append("分钟前");
        } else if (seconds < SECONDS_PER_DAY) {
            buffer.append(seconds / SECONDS_PER_HOUR).append("小时前");
        } else if (seconds < SECONDS_PER_MONTH) {
            buffer.append(seconds / SECONDS_PER_DAY).append("天前");
        } else if (seconds < SECONDS_PER_YEAR) {
            buffer.append(seconds / SECONDS_PER_MONTH).append("月前");
        } else {
            buffer.append(seconds / DateUtils.SECONDS_PER_YEAR).append("年前");
        }
        return buffer.toString();
    }


    /**
     * 记录时间相当于目前多久之前
     * @param timestamp 时间戳
     * @return String 时间描述 分钟前、小时前、天前、月前、年前
     */
    public static String getIntervalByTimestamp(Timestamp timestamp) {
        String timeStr = "";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long time = (now.getTime() - timestamp.getTime()) / 1000;
        if (time < 60) {
            timeStr = "刚刚";
        } else if (time < 60 * 60) {
            timeStr = time / 60 + "分钟前";
        } else if (time < 24 * 60 * 60) {
            timeStr = time / 60 / 60 + "小时前";
        } else if (time < 30 * 24 * 60 * 60) {
            timeStr = time / 24 / 60 / 60 + "天前";
        } else if (time < 365 * 24 * 60 * 60) {
            timeStr = time / 30 / 24 / 60 / 60 + "月前";
        } else {
            timeStr = time / 365 / 24 / 60 / 60 + "年前";
        }
        return timeStr;
    }


    /**
     * 查询两个日期相隔的月份
     * @param startDate 开始日期1 (格式yyyy-MM-dd)
     * @param endDate   截止日期2 (格式yyyy-MM-dd)
     * @return int
     */
    public static int getMonthsBetween(String startDate, String endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateUtils.parseDate(startDate));
        c2.setTime(DateUtils.parseDate(endDate));
        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int month = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(year * 12 + month);
    }

    /**
     * 根据日期字符串 获取指定日期是星期几
     * @param dateStr 日期："2020-06-06"
     * @return 字符串：星期几
     */
    public static String getDayOfWeek(String dateStr) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Date date = parseDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekOfDays[num];
    }

    /**
     * 根据日期 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     * @param date
     * @return 字符串：星期几
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * 对日期进行加减操作
     * @param date       要进行加减天数的日期
     * @param addOrMinus 对日期加减天数（eg：加一天：1 减一天：-1）
     * @return Date
     */
    public static Date dateAddOrMinus(Date date, Integer addOrMinus) {
        if (addOrMinus == null) {
            addOrMinus = 0;
        }
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, +addOrMinus);
        return cal.getTime();
    }

    /**
     * 时间 增加、减少 n个小时以后时间
     * @param date YYYY-mm-dd HH:mm:ss
     * @param num 小时数
     * @param type 0：减少
     * @return 日期Date
     */
    public static Date adjustDateByHour(Date date, Integer num, int type) {
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTime(date);
        if (type == 0) {
            cal.add(Calendar.MINUTE, -num);
        } else {
            cal.add(Calendar.MINUTE, num);
        }
        return cal.getTime();
    }

}
