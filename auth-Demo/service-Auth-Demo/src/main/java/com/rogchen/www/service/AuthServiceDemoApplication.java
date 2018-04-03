package com.rogchen.www.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class AuthServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceDemoApplication.class, args);
	}
}
