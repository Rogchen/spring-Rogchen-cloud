package com.rogchen.www.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/4/4 09:40
 **/
@RestController
public class TestAuthController {

    @RequestMapping("test")
    public String getTest(){
        return "test";
    }
}
