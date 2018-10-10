package com.rogchen.email.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen rogchen128@gmail.com
 * @Created Date: 18-10-10 22:14
 **/
@Service
public class JavaMailService {

    @Cacheable(value = "javamail", key = "javamail")
    public List<Map<String, String>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("senderAddress", "######@qq.com");
        map.put("recipientAddress", "######@qq.com");
        map.put("senderAccount", "######@qq.com");
        map.put("host", "smtp.qq.com");
        map.put("senderPassword", "######");
        list.add(map);
        map = new HashMap<>();
        map.put("senderAddress", "######@163.com");
        map.put("recipientAddress", "######@qq.com");
        map.put("senderAccount", "######@163.com");
        map.put("host", "smtp.163.com");
        map.put("senderPassword", "######");
        list.add(map);
        return list;
    }
}
