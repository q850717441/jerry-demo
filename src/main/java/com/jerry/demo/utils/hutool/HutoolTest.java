package com.jerry.demo.utils.hutool;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jerry.demo.controller.TestController;
import com.jerry.demo.domain.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: Jerry
 * @create: 2020-09-08 15:10
 * @update: 2020-09-08 15:10
 * @description: 学习hutool工具API
 */
@Slf4j
public class HutoolTest {
    /**
     * 类型转换工具类，用于各种类型数据的转换。
     * 平时我们转换类型经常会面临类型转换失败的问题，要写try catch代码，有了它，就不用写了！
     */
    @Test
    public void convert() {
        //转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        //转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);
        //转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);
        //转换为列表
        String[] strArr = {"a", "b", "c", "d"};
        List<String> strList = Convert.toList(String.class, strArr);
    }

    /**
     * 日期时间工具类，定义了一些常用的日期时间操作方法。
     * JDK自带的Date和Calendar对象真心不好用，有了它操作日期时间就简单多了！
     */
    @Test
    public void dateUtil() {
        //Date、long、Calendar之间的相互转换
        Date date;
        //当前时间
        date = DateUtil.date();
        //Calendar转Date
        date = DateUtil.date(Calendar.getInstance());
        //时间戳转Date
        date = DateUtil.date(System.currentTimeMillis());
        //自动识别格式转换
        String dateStr = "2017-03-01";
        date = DateUtil.parse(dateStr);
        //自定义格式化转换
        date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        //格式化输出日期
        String format = DateUtil.format(date, "yyyy-MM-dd");
        //获得年的部分
        int year = DateUtil.year(date);
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        //获取某天的开始、结束时间
        Date beginOfDay = DateUtil.beginOfDay(date);
        Date endOfDay = DateUtil.endOfDay(date);
        //计算偏移后的日期时间
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //计算日期时间之间的偏移量
        long betweenDay = DateUtil.between(date, newDate, DateUnit.DAY);
    }

    /**
     * JSON解析工具类，可用于对象与JSON之间的互相转化。
     */
    @Test
    public void JSONUtil() {
        User user = new User();
        user.setId(1L);
        user.setName("小米");
        //对象转化为JSON字符串
        String jsonStr = JSONUtil.parse(user).toString();
        log.info("jsonUtil parse:{}", jsonStr);
        //JSON字符串转化为对象
        User userBean = JSONUtil.toBean(jsonStr, User.class);
        log.info("jsonUtil toBean:{}", userBean);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        String jsonListStr = JSONUtil.parse(userList).toString();
        //JSON字符串转化为列表
        userList = JSONUtil.toList(new JSONArray(jsonListStr), User.class);
        log.info("jsonUtil toList:{}", userList);
    }

    /**
     * 字符串工具类，定义了一些常用的字符串操作方法。
     * StrUtil比StringUtil名称更短，用起来也更方便！
     */
    @Test
    public void StrUtil() {
        //判断是否为空字符串
        String str = "test";
        StrUtil.isEmpty(str);
        StrUtil.isBlank(str);
        StrUtil.isNotEmpty(str);
        //去除字符串的前后缀
        StrUtil.removeSuffix("a.jpg", ".jpg");
        StrUtil.removePrefix("a.jpg", "a.");
        //格式化字符串
        String template = "这只是个占位符:{}";
        String str2 = StrUtil.format(template, "我是占位符");
        log.info("/strUtil format:{}", str2);
    }

    /**
     * ClassPath单一资源访问类，可以获取classPath下的文件，在Tomcat等容器下，classPath一般是WEB-INF/classes。
     * @throws IOException
     */
    @Test
    public void ClassPathResource() throws IOException {
        //获取定义在src/main/resources文件夹中的配置文件
        ClassPathResource resource = new ClassPathResource("application.yml");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        log.info("/classPath:{}", properties);
    }

    /**
     * Java反射工具类，可用于反射获取类的方法及创建对象。
     */
    @Test
    public void ReflectUtil() {
        //获取某个类的所有方法
        Method[] methods = ReflectUtil.getMethods(User.class);
        //获取某个类的指定方法
        Method method = ReflectUtil.getMethod(User.class, "getId");
        //使用反射来创建对象
        User user = ReflectUtil.newInstance(User.class);
        //反射执行对象的方法
        ReflectUtil.invoke(user, "setId", 1);
    }

    /**
     * 数字处理工具类，可用于各种类型数字的加减乘除操作及类型判断。
     */
    @Test
    public void NumberUtil() {
        double n1 = 1.234;
        double n2 = 1.234;
        double result;
        //对float、double、BigDecimal做加减乘除操作
        result = NumberUtil.add(n1, n2);
        result = NumberUtil.sub(n1, n2);
        result = NumberUtil.mul(n1, n2);
        result = NumberUtil.div(n1, n2);
        //保留两位小数
        BigDecimal roundNum = NumberUtil.round(n1, 2);
        String n3 = "1.234";
        //判断是否为数字、整数、浮点数
        NumberUtil.isNumber(n3);
        NumberUtil.isInteger(n3);
        NumberUtil.isDouble(n3);
    }

    /**
     * JavaBean工具类，可用于Map与JavaBean对象的互相转换以及对象属性的拷贝。
     */
    @Test
    public void BeanUtil() {
        User user = new User();
        user.setId(1L);
        user.setName("小米");
        //Bean转Map
        Map<String, Object> map = BeanUtil.beanToMap(user);
        log.info("beanUtil bean to map:{}", map);
        //Map转Bean
        User mapUser = BeanUtil.mapToBean(map, User.class, false);
        log.info("beanUtil map to bean:{}", mapUser);
        //Bean属性拷贝
        User copyUser = new User();
        BeanUtil.copyProperties(user, copyUser);
        log.info("beanUtil copy properties:{}", copyUser);
    }


    /**
     * 集合操作的工具类，定义了一些常用的集合操作。
     */
    @Test
    public void CollUtil() {
        //数组转换为列表
        String[] array = new String[]{"a", "b", "c", "d", "e"};
        List<String> list = CollUtil.newArrayList(array);
        //join：数组转字符串时添加连接符号
        String joinStr = CollUtil.join(list, ",");
        log.info("collUtil join:{}", joinStr);
        //将以连接符号分隔的字符串再转换为列表
        List<String> splitList = StrUtil.split(joinStr, ',');
        log.info("collUtil split:{}", splitList);
        //创建新的Map、Set、List
        HashMap<Object, Object> newMap = CollUtil.newHashMap();
        HashSet<Object> newHashSet = CollUtil.newHashSet();
        ArrayList<Object> newList = CollUtil.newArrayList();
        //判断列表是否为空
        CollUtil.isEmpty(list);
    }


    /**
     * Map操作工具类，可用于创建Map对象及判断Map是否为空。
     */
    @Test
    public void MapUtil() {
        //将多个键值对加入到Map中
        Map<Object, Object> map = MapUtil.of(new String[][]{
                {"key1", "value1"},
                {"key2", "value2"},
                {"key3", "value3"}
        });
        //判断Map是否为空
        MapUtil.isEmpty(map);
        MapUtil.isNotEmpty(map);
    }

    /**
     * 注解工具类，可用于获取注解与注解中指定的值。
     */
    @Test
    public void AnnotationUtil() {
        //获取指定类、方法、字段、构造器上的注解列表
        Annotation[] annotationList = AnnotationUtil.getAnnotations(TestController.class, false);
        log.info("annotationUtil annotations:{}", annotationList);
        //获取指定类型注解
        Api api = AnnotationUtil.getAnnotation(TestController.class, Api.class);
        log.info("annotationUtil api value:{}", api.description());
        //获取指定类型注解的值
        Object annotationValue = AnnotationUtil.getAnnotationValue(TestController.class, RequestMapping.class);
    }

    /**
     * 加密解密工具类，可用于MD5加密。
     */
    @Test
    public void SecureUtil() {
        //MD5加密
        String str = "123456";
        String md5Str = SecureUtil.md5(str);
        log.info("secureUtil md5:{}", md5Str);
    }

    /**
     * 验证码工具类，可用于生成图形验证码。
     */
    @Test
    public void CaptchaUtil() {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        //生成验证码图片
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        try {
            request.getSession().setAttribute("CAPTCHA_KEY", lineCaptcha.getCode());
            response.setContentType("image/png");//告诉浏览器输出内容为图片
            response.setHeader("Pragma", "No-cache");//禁止浏览器缓存
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字段验证器，可以对不同格式的字符串进行验证，比如邮箱、手机号、IP等格式。
     */
    @Test
    public void Validator() {
        //判断是否为邮箱地址
        boolean result = Validator.isEmail("macro@qq.com");
        log.info("Validator isEmail:{}", result);
        //判断是否为手机号码
        result = Validator.isMobile("18911111111");
        log.info("Validator isMobile:{}", result);
        //判断是否为IPV4地址
        result = Validator.isIpv4("192.168.3.101");
        log.info("Validator isIpv4:{}", result);
        //判断是否为汉字
        result = Validator.isChinese("你好");
        log.info("Validator isChinese:{}", result);
        //判断是否为身份证号码（18位中国）
        result = Validator.isCitizenId("123456");
        log.info("Validator isCitizenId:{}", result);
        //判断是否为URL
        result = Validator.isUrl("http://www.baidu.com");
        log.info("Validator isUrl:{}", result);
        //判断是否为生日
        result = Validator.isBirthday("2020-02-01");
        log.info("Validator isBirthday:{}", result);
    }

    /**
     * 摘要算法工具类，支持MD5、SHA-256、Bcrypt等算法。
     */
    @Test
    public void DigestUtil() {
        String password = "123456";
        //计算MD5摘要值，并转为16进制字符串
        String result = DigestUtil.md5Hex(password);
        log.info("DigestUtil md5Hex:{}", result);
        //计算SHA-256摘要值，并转为16进制字符串
        result = DigestUtil.sha256Hex(password);
        log.info("DigestUtil sha256Hex:{}", result);
        //生成Bcrypt加密后的密文，并校验
        String hashPwd = DigestUtil.bcrypt(password);
        boolean check = DigestUtil.bcryptCheck(password, hashPwd);
        log.info("DigestUtil bcryptCheck:{}", check);
    }

    /**
     * Http请求工具类，可以发起GET/POST等请求。
     */
    @Test
    public void HttpUtil() {
        String response = HttpUtil.get("101.132.178.101:9101/public/image/indexList?page=1&limit=10");
        log.info("HttpUtil get:{}", response);
    }

    /**
     * HTML 工具类
     */
    @Test
    public void HtmlUtil() {
        //可以将一些字符转化为安全字符，防止 xss 注入和 SQL 注入
        String textStr = HtmlUtil.escape("test111111111");
        /**
         * HtmlUtil.restoreEscaped 还原被转义的 HTML 特殊字符
         * HtmlUtil.escape 转义文本中的 HTML 字符为安全的字符
         * HtmlUtil.cleanHtmlTag 清除所有 HTML 标签
         * HtmlUtil.removeHtmlTag 清除指定 HTML 标签和被标签包围的内容
         * HtmlUtil.unwrapHtmlTag 清除指定 HTML 标签，不包括内容
         * HtmlUtil.removeHtmlAttr 去除 HTML 标签中的属性
         * HtmlUtil.removeAllHtmlAttr 去除指定标签的所有属性
         * HtmlUtil.filter 过滤 HTML 文本，防止 XSS 攻击
         */
    }

    /**
     *
     */
    @Test
    public void test() {

    }

}
