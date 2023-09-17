package com.example.rmsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RmsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmsGatewayApplication.class, args);
	}

}
