package com.rogchen.www.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description:
 * @Product: IntelliJ IDEA
 * @Author Rogchen chenhk128@163.com
 * @Created Date: 2018/3/20 11:10
 **/
@SpringBootApplication
@EnableEurekaClient
public class MybatisDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(MybatisDemoApplication.class, args);
    }
}
