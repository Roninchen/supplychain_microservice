package com.disanbo.service.supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.disanbo")
public class SupplyTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplyTestServiceApplication.class, args);
    }
}
