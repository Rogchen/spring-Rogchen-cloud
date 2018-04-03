package com.rogchen.www.mybatis.controller;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/3/20 15:26
 **/
@Controller
@RequestMapping("rog")
public class MybatisDemoController {


    @GetMapping(value = "/getMap")
    @ResponseBody
    public Map getmap(){
        Map map = Maps.newHashMap();


        return map;
    }
}
