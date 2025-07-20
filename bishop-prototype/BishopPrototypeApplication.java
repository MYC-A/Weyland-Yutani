package com.REST_API.bishop_prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.REST_API.bishop_prototype", "com.weyland.starter"})

@SpringBootApplication
public class BishopPrototypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BishopPrototypeApplication.class, args);
	}

}
