package com.rogchen.www.feign;

import com.ylzinfo.cloud.auth.client.feign.AuthFeignConfiguration;
import com.ylzinfo.dto.impl.CommonResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sms-service", configuration = AuthFeignConfiguration.class)
public interface SmsService {
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public CommonResult sendSms(@RequestParam("phoneNumbers") String phoneNumbers, @RequestParam("content") String content);
}