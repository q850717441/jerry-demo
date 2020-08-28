package com.jerry.demo.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author: Jerry
 * @create: 2020-08-27 15:44
 * @update: 2020-08-27
 * @description: cookie工具
 */
public class CookieUtil {
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        //校验cookie是否有效.
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void deleteCookie(HttpServletResponse response, String cookieName, int maxAge, String domain, String path) {
        Cookie jtCookie = new Cookie(cookieName, "");
        jtCookie.setMaxAge(maxAge); //表示立即删除
        jtCookie.setDomain(domain);//设置cookie共享
        jtCookie.setPath(path);
        response.addCookie(jtCookie);
    }
}
