package com.rogchen.www.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/3/19 15:28
 **/
@Controller
@RequestMapping("feign")
@Slf4j
public class FeignDemoController {

    @GetMapping(value = "/getById")
    @ResponseBody
    public Map feignDemo(@RequestParam("id")  String id){
        log.info("*********************");
        log.info(id);
        log.info("*********************");
        Map map = Maps.newHashMap();
        map.put("id",id);
        return map;
    }
}
