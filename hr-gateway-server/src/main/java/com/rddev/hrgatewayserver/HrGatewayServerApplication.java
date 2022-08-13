package com.rddev.hrgatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
public class HrGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrGatewayServerApplication.class, args);
	}

}
