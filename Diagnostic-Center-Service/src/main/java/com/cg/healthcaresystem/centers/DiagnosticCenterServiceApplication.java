package com.cg.healthcaresystem.centers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/* 
 * @SpringBootApplication annotation does the combined work for @ComponentScan, @Configuration and @EnableAutoConfiguration.
 * @EnableEurekaClient annotation mark the service as Discovery client to register itself on the EurekaServer
 */

@SpringBootApplication
@EnableEurekaClient
public class DiagnosticCenterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosticCenterServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}