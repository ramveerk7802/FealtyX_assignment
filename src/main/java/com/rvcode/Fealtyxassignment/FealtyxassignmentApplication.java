package com.rvcode.Fealtyxassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FealtyxassignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FealtyxassignmentApplication.class, args);
	}

}
