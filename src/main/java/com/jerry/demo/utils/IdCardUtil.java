package com.jerry.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author: Jerry
 * @create: 2020-05-22 17:47
 * @update: 2020-05-22
 * @description: 身份证工具类
 */
public class IdCardUtil {
    /** 中国公民身份证号码最小长度。 */
    public final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public final int CHINA_ID_MAX_LENGTH = 18;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    // 身份证校验码
    private static final int[] COEFFICIENT_ARRAY = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 身份证号的尾数规则
    private static final String[] IDENTITY_MANTISSA = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    private static final String IDENTITY_PATTERN = "^[0-9]{17}[0-9Xx]$";

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard
     *            身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     *            身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     *            身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     * @param idCard 身份编号
     * @return 性别(M - 男 ， F - 女 ， N - 未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "N";
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "M";//男
        } else {
            sGender = "F";//女
        }
        return sGender;
    }

    /**
     * 15位转18位
     * @param str
     * @return
     */
    public static String trans15bitTo18bit(String str) {
        String[] input = str.split("");
        String[] result = new String[18];
        for (int i = 0; i < input.length; i++) {
            if (i <= 5) {
                result[i] = input[i];
            } else {
                result[i + 2] = input[i];
            }
        }
        //年份最后两位小于17,年份为20XX，否则为19XX
        if (Integer.valueOf(input[6]) <= 1 && Integer.valueOf(input[7]) <= 7) {
            result[6] = "2";
            result[7] = "0";
        } else {
            result[6] = "1";
            result[7] = "9";
        }
        //计算最后一位
        String[] xs = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        //前十七位乘以系数[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2],
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.valueOf(result[i]) * Integer.valueOf(xs[i]);
        }
        //对11求余，的余数 0 - 10
        int rod = sum % 11;
        //所得余数映射到对应数字即可
        if (rod == 0) {
            result[17] = "1";
        } else if (rod == 1) {
            result[17] = "0";
        } else if (rod == 2) {
            result[17] = "X";
        } else if (rod == 3) {
            result[17] = "9";
        } else if (rod == 4) {
            result[17] = "8";
        } else if (rod == 5) {
            result[17] = "7";
        } else if (rod == 6) {
            result[17] = "6";
        } else if (rod == 7) {
            result[17] = "5";
        } else if (rod == 8) {
            result[17] = "4";
        } else if (rod == 9) {
            result[17] = "3";
        } else if (rod == 10) {
            result[17] = "2";
        }

        StringBuffer s = new StringBuffer();
        for (String c : result) {
            s.append(c);
        }
        String string = s.toString();
        return string;
    }


    /**
     * 18位身份证获取性别和年龄
     * @param CardCode
     * @return
     * @throws Exception
     */
    public static Map<String, Object> identityCard18(String CardCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到年份
        String year = CardCode.substring(6).substring(0, 4);
        // 得到月份
        String month = CardCode.substring(10).substring(0, 2);
        //得到日
        //String day=CardCode.substring(12).substring(0,2);
        String sex;
        // 判断性别
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        // 得到当前的系统时间
        Date date = new Date();
        // 当前年份
        String currentYear = format.format(date).substring(0, 4);
        // 月份
        String currentMonth = format.format(date).substring(5, 7);
        //String currentdDay=format.format(date).substring(8,10);
        int age = 0;
        // 当前月份大于用户出身的月份表示已过生日
        if (Integer.parseInt(month) <= Integer.parseInt(currentMonth)) {
            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
        } else {
            // 当前用户还没过生日
            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    /**
     * 15位身份证获取性别和年龄
     * @param card
     * @return
     * @throws Exception
     */
    public static Map<String, Object> identityCard15(String card) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        //年份
        String year = "19" + card.substring(6, 8);
        //月份
        String yue = card.substring(8, 10);
        //日
        //String day=card.substring(10, 12);
        String sex;
        if (Integer.parseInt(card.substring(14, 15)) % 2 == 0) {
            sex = "女";
        } else {
            sex = "男";
        }
        // 得到当前的系统时间
        Date date = new Date();
        //当前年份
        String currentYear = format.format(date).substring(0, 4);
        //月份
        String currentMonth = format.format(date).substring(5, 7);
        //String fday=format.format(date).substring(8,10);
        int age = 0;
        //当前月份大于用户出身的月份表示已过生日
        if (Integer.parseInt(yue) <= Integer.parseInt(currentMonth)) {
            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
        } else {
            // 当前用户还没过生日
            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    /**
     * 身份证号码验证
     * 1、号码的结构
     * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
     * 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
     * 顺序码的奇数分配给男性，偶数分配给女性。
     * 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai Wi), i = 0, , 16 ，先对前17位数字的权求和 ;
     * Ai:表示第i位置上的身份证号码数字值; Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模( 0 1 2 3 4 5 6 7 8 9 10)得到对应的校验码 Y:1 0 X 9 8 7 6 5 4 3 2
     */
    public static boolean isLegalPattern(String identity) {
        if (identity == null) {
            return false;
        }

        if (identity.length() != 18) {
            return false;
        }

        if (!identity.matches(IDENTITY_PATTERN)) {
            return false;
        }

        char[] chars = identity.toCharArray();
        long sum = IntStream.range(0, 17).map(index -> {
            char ch = chars[index];
            int digit = Character.digit(ch, 10);
            int coefficient = COEFFICIENT_ARRAY[index];
            return digit * coefficient;
        }).summaryStatistics().getSum();

        // 计算出的尾数索引
        int mantissaIndex = (int) (sum % 11);
        String mantissa = IDENTITY_MANTISSA[mantissaIndex];

        String lastChar = identity.substring(17);
        if (lastChar.equalsIgnoreCase(mantissa)) {
            return true;
        } else {
            return false;
        }
    }
}
