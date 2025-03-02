package com.bank.bank_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.bank")
public class BankDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankDemoApplication.class, args);
	}

}
