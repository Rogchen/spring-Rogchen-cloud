package com.rogchen.www.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/4/4 09:40
 **/
@RestController
@RefreshScope       //非常重要
public class TestAuthController {
    @Value(value = "${rogchen.test}")
    private String test;

    @RequestMapping("test")
    public String getTest(){
        System.out.println("===================");
        System.out.println(test);
        return "test"+test;
    }
}
