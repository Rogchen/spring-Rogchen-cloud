package com.rogchen.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JavamailApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavamailApplication.class, args);
	}
}
