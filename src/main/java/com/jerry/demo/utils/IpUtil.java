package com.jerry.demo.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: Jerry
 * @create: 2020-08-27 14:30
 * @update: 2020-09-07
 * @description: IP工具类
 */
@Slf4j
public class IpUtil {
    private static final String UNKNOWN = "unknown";
    /** 腾讯位置服务key配置 腾讯位置服务官网注册添加key */
    private static final String QQLBS_KEY = "你的腾讯位置服务key";

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            //Proxy-Client-IP：apache 服务代理
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            //X-Real-IP：nginx服务代理
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取客户端主机名称
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
        return "未知";
    }


    /**
     * 获取IP返回地理信息
     * @param
     * @return
     */
    public String getIpCity(HttpServletRequest request){

        String url = "https://apis.map.qq.com/ws/location/v1/ip?key="+ QQLBS_KEY +"&ip=" + getIpAddr(request);
        String result = "未知";
        try {
            String json = HttpUtil.get(url, 3000);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String status = jsonObject.get("status").getAsString();
            if("0".equals(status)){
                JsonObject adInfo = jsonObject.get("result").getAsJsonObject().get("ad_info").getAsJsonObject();
                String nation = adInfo.get("nation").getAsString();
                String province = adInfo.get("province").getAsString();
                String city = adInfo.get("city").getAsString();
                String district = adInfo.get("district").getAsString();
                if(StrUtil.isNotBlank(nation)&&StrUtil.isBlank(province)){
                    result = nation;
                } else {
                    result = province;
                    if(StrUtil.isNotBlank(city)){
                        result += " " + city;
                    }
                    if(StrUtil.isNotBlank(district)){
                        result += " " + district;
                    }
                }
            }
        } catch (Exception e) {
            log.info("获取IP地理信息失败");
        }
        return result;
    }
}
