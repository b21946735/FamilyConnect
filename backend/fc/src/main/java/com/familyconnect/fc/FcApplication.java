package com.familyconnect.fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcApplication.class, args);

		System.out.println("Hello World");
	}

}
