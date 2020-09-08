package com.jerry.demo.controller;

import com.jerry.demo.mapper.UserMapper;
import com.jerry.demo.utils.IpUtil;
import com.jerry.demo.utils.common.DataResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author: Jerry
 * @create: 2020-08-27 15:09
 * @description: 测试功能接口
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/one")
    public DataResult one(HttpServletRequest request) {
        String ipAddr = IpUtil.getIpAddr(request);
        return DataResult.success(ipAddr);
    }

    @RequestMapping("/two")
    public DataResult test() {
        userMapper.findUserById(1);
        return DataResult.success();
    }

    public static void main(String[] args) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        percent.setMaximumFractionDigits(3); //百分比小数点最多3位

        BigDecimal loanAmount = new BigDecimal("15000.48"); //贷款金额
        BigDecimal interestRate = new BigDecimal("0.008"); //利率
        BigDecimal interest = loanAmount.multiply(interestRate); //相乘

        System.out.println("贷款金额:\t" + currency.format(loanAmount));
        System.out.println("利率:\t" + percent.format(interestRate));
        System.out.println("利息:\t" + currency.format(interest));
    }
}
