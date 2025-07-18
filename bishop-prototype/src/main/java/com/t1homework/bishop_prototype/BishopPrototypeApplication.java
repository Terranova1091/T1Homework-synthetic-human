package com.t1homework.bishop_prototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.t1homework.bishop_prototype", "com.t1homework.starter"})
public class BishopPrototypeApplication {
	public BishopPrototypeApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(BishopPrototypeApplication.class, args);
	}
}
