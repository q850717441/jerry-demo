package com.jerry.demo.controller;

import com.jerry.demo.utils.common.DataResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Jerry
 * @create: 2020-07-22 10:59
 * @description:
 */
@Api(tags = "HELLO CONTROLLER 测试功能接口")
@RestController
@RequestMapping("knife4j")
public class Knife4jController {
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "接口返回成功状态"),
            @ApiResponse(code = 500, message = "接口返回未知错误，请联系开发人员调试")
    })
    @GetMapping(value = "/test1")
    public DataResult test1() {
        return DataResult.success("hello word");
    }

}
