package com.rogchen.www.controller;

import com.google.common.collect.Maps;
import com.rogchen.www.feign.MsgInterfaceService;
import com.rogchen.www.feign.SmsService;
import com.ylzinfo.dto.impl.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MsgInterfaceService msgInterfaceService;
    @Autowired
    private SmsService smsService;

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


    @GetMapping(value = {"","/"})
    @ResponseBody
    public void get(){
//        Result t = msgInterfaceService.getFeign();
//        System.out.println(t);
//        Result result = msgInterfaceService.getContentList(10007l,null,"10","1");
//        System.out.println(result);
//        Result rt = msgInterfaceService.getAppSendRecord("6D41C12969834B2AE0531F02660A9824",10,1);
//        System.out.println(rt);
        CommonResult rt = smsService.sendSms("15677079765","123445455555");
        System.out.println(rt);
    }
}
