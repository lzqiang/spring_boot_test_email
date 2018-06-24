package com.ttpai.test.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.ttpai.test"})
public class TestEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestEmailApplication.class, args);
	}
}
