package com.example.bakend_vape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BakendVapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakendVapeApplication.class, args);
	}

}
