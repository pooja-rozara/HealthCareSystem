package com.cg.healthCareSystem.appointmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringSecurityDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemo1Application.class, args);
	}

}
