package com.jerry.demo.utils.hutool;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jerry
 * @create: 2020-10-09 10:18
 * @update: 2020-10-09 10:18
 * @description: 核心，包括Bean操作、日期、各种Util等
 */
@Slf4j
public class HutoolCoreTest {
    /**
     * 类型转换工具类，用于各种类型数据的转换。
     * 平时我们转换类型经常会面临类型转换失败的问题，要写try catch代码，有了它，就不用写了！
     * Convert类中大部分方法为toXXX，参数为Object，可以实现将任意可能的类型转换为指定类型。
     * 同时支持第二个参数defaultValue用于在转换失败时返回一个默认值。
     */
    @Test
    public void convert() {
        //1.转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        long[] b1 = {1, 2, 3, 4, 5};
        //bStr为："[1, 2, 3, 4, 5]"
        String bStr = Convert.toStr(b1);

        //2.转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);

        //3.转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);

        //4.转换为集合
        Object[] arr = {"a", "你", "好", "", 1};
        List<?> list = Convert.toList(arr);

        //5.通过TypeReference实例化后制定泛型类型，即可转换对象为我们想要的目标类型。
        List<String> strList = Convert.convert(new TypeReference<List<String>>() {
        }, a);

        //6.半角和全角转换
        //半角转全角：
        String a1 = "123456789";
        String sbc = Convert.toSBC(a1);     //结果为："１２３４５６７８９"
        //全角转半角：
        String a2 = "１２３４５６７８９";
        String dbc = Convert.toDBC(a2);  //结果为"123456789"

        //7. 16进制（Hex） 在很多加密解密，以及中文字符串传输（比如表单提交）的时候，会用到16进制转换，就是Hex转换
        //转为16进制（Hex）字符串
        String a3 = "我是一个小小的可爱的字符串";
        String hex = Convert.toHex(a3, CharsetUtil.CHARSET_UTF_8);//结果："e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2"
        //将16进制（Hex）字符串转为普通字符串
        String raw = Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8);//结果为："我是一个小小的可爱的字符串"

        //8.Unicode和字符串转换
        String a4 = "我是一个小小的可爱的字符串";
        String unicode = Convert.strToUnicode(a4);//结果为："\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32"
        String raw1 = Convert.unicodeToStr(unicode);//结果为："我是一个小小的可爱的字符串"

        //9.编码转换  在接收表单的时候，我们常常被中文乱码所困扰，其实大多数原因是使用了不正确的编码方式解码了数据。
        String a9 = "我不是乱码";
        String result = Convert.convertCharset(a9, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1); //转换后result为乱码
        String raw9 = Convert.convertCharset(result, CharsetUtil.ISO_8859_1, "UTF-8");
        Assert.assertEquals(raw, a9);

        //10.时间单位转换  Convert.convertTime方法主要用于转换时长单位，比如一个很大的毫秒，我想获得这个毫秒数对应多少分：
        long a10 = 4535345;
        long minutes = Convert.convertTime(a10, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);        //结果为：75

        //11.金额大小写转换 :面对财务类需求，Convert.digitToChinese将金钱数转换为大写形式：
        double a11 = 67556.32;//注意 转换为大写只能精确到分（小数点儿后两位），之后的数字会被忽略。
        String digitUppercase = Convert.digitToChinese(a11);//结果为："陆万柒仟伍佰伍拾陆元叁角贰分"

        //12.原始类和包装类转换:将包装类和原始类相互转换（比如Integer.class 和 int.class
        Class<?> wrapClass = Integer.class;//包装
        Class<?> unWraped = Convert.unWrap(wrapClass);//结果为：int.class
        Class<?> primitiveClass = long.class;//包装
        Class<?> wraped = Convert.wrap(primitiveClass);//结果为：Long.class
    }

    @Test
    public void test() {

    }

}
