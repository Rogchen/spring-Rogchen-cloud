package com.rogchen.lockDemo.controller;

import com.rogchen.lockDemo.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 2018/9/30 13:33
 **/
@Controller
public class LockController {
    @Autowired
    private LockService lockService;

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        lockService.insertName();
        return "abc";
    }
}
