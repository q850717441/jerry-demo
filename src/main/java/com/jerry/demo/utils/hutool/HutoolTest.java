package com.jerry.demo.utils.hutool;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jerry.demo.common.constant.DateFormatEnum;
import com.jerry.demo.controller.TestController;
import com.jerry.demo.domain.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
        date = DateUtil.parse(dateStr, DateFormatEnum.DATE_FORMAT2.getValue());
        //格式化输出日期
        String format = DateUtil.format(date, DateFormatEnum.DATE_FORMAT2.getValue());
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
        //JSON字符串转JSONObject对象
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String s1 = jsonObject.getStr("属性名");
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
     * 文件工具
     */
    @Test
    public void fileUtil() {
        //获得文件的扩展名（后缀名），扩展名不带“.”
        String extName = FileUtil.extName("/Users/jerry/Files/oss/testVideo.mp4");

        //创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
        File file = FileUtil.file("/Users/jerry/Files/oss/testVideo.mp4");
        //修改文件或目录的文件名，不变更路径，只是简单修改文件名
        File testVideo1 = FileUtil.rename(file, "testVideo1", false);
    }

    /**
     * 唯一ID工具
     * 在分布式环境中，唯一ID生成应用十分广泛，生成方法也多种多样，Hutool针对一些常用生成策略做了简单封装。
     * 唯一ID生成器的工具类，涵盖了：
     * UUID：全称通用唯一识别码（universally unique identifier），JDK通过java.util.UUID提供了 Leach-Salz 变体的封装。
     * ObjectId（MongoDB）:ObjectId是MongoDB数据库的一种唯一ID生成策略，是UUID version1的变种
     * Snowflake（Twitter）：分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器。
     */
    @Test
    public void idUtil() {
        // Hutool重写java.util.UUID的逻辑，对应类为cn.hutool.core.lang.UUID，使生成不带-的UUID字符串不再需要做字符替换，性能提升一倍左右。
        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        String uuid = IdUtil.randomUUID();
        System.out.println("uuid = " + uuid);
        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        System.out.println("simpleUUID = " + simpleUUID);

        //ObjectId 生成类似：5b9e306a4df4f8c54a39fb0c
        String id2 = IdUtil.objectId();
        System.out.println("id2 = " + id2);

        //注意：IdUtil.createSnowflake每次调用会创建一个新的Snowflake对象，不同的Snowflake对象创建的ID可能会有重复，
        // 因此请自行维护此对象为单例，或者使用IdUtil.getSnowflake使用全局单例对象。
        //参数1为终端ID,参数2为数据中心ID
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println("id = " + id);
    }

    /**
     * 身份证工具
     * IdcardUtil 支持大陆15位、18位身份证，港澳台10位身份证。
     * isValidCard 验证身份证是否合法
     * convert15To18 身份证15位转18位
     * getBirthByIdCard 获取生日
     * getAgeByIdCard 获取年龄
     * getYearByIdCard 获取生日年
     * getMonthByIdCard 获取生日月
     * getDayByIdCard 获取生日天
     * getGenderByIdCard 获取性别
     * getProvinceByIdCard 获取省份
     */
    @Test
    public void idcardUtil() {
        String ID_18 = "320684199611085935";
        String ID_15 = "150102880730303";
        //是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);
        //转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        Assert.assertEquals(convert15To18, "150102198807303035");
        //年龄
        DateTime date = DateUtil.parse("2020-09-22");
        int age = IdcardUtil.getAgeByIdCard(ID_18, date);
        //生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        //省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
    }

    /**
     * Unicode编码转换工具
     * 此工具主要针对类似于\u4e2d\u6587这类Unicode字符做一些特殊转换。
     */
    @Test
    public void unicodeUtil() {
        //Unicode转字符串
        String str = "\\u8f6c\\u7801\\u4efb\\u52a1\\u5df2\\u53d1\\u8d77";
        String res = UnicodeUtil.toString(str);
        //字符串转Unicode符
        String s = UnicodeUtil.toUnicode(res, true);
    }

    /**
     * 随机工具
     * RandomUtil主要针对JDK中Random对象做封装，严格来说，Java产生的随机数都是伪随机数，因此Hutool封装后产生的随机结果也是伪随机结果。
     * 不过这种随机结果对于大多数情况已经够用。
     */
    @Test
    public void randomUtil() {
        //获得一个随机的字符串（只包含数字和字符）
        System.out.println("随机的字符串（只包含数字和字符）" + RandomUtil.randomString(10));
        //获得一个只包含数字的字符串
        System.out.println("只包含数字的字符串:" + RandomUtil.randomNumbers(10));
        //获得指定范围内的随机数
        byte[] bytes = RandomUtil.randomBytes(10);
        System.out.println(Arrays.toString(bytes));
//        RandomUtil.weightRandom 权重随机生成器，传入带权重的对象，然后根据权重随机获取对象

        //随机获得列表中的元素
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(RandomUtil.randomEle(list));
        //随机获得列表中的一定量的不重复元素，返回Set
        Set<Integer> integers = RandomUtil.randomEleSet(list, 2);
        integers.forEach(System.out::println);

    }


    /**
     *
     */
    @Test
    public void test() {

    }

}
