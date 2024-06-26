package com.usermicroservice.UserMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMicroServiceApplication.class, args);
    }
}
