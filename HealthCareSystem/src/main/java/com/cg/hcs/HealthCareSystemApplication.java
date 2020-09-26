package com.cg.hcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HealthCareSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthCareSystemApplication.class, args);
	}

}
