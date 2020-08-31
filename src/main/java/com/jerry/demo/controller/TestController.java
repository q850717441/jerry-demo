package com.jerry.demo.controller;

import com.jerry.demo.utils.IpUtil;
import com.jerry.demo.utils.common.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Jerry
 * @create: 2020-08-27 15:09
 * @description: 测试功能接口
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/one")
    public DataResult one(HttpServletRequest request) {
        String ipAddr = IpUtil.getIpAddr(request);
        return DataResult.success(ipAddr);
    }
}