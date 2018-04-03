package com.rogchen.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurakaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurakaDemoApplication.class, args);
	}
}
