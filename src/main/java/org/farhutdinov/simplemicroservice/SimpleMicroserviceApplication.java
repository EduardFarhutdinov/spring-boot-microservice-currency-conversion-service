package org.farhutdinov.simplemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("org.farhutdinov.simplemicroservice.service")
@EnableDiscoveryClient
public class SimpleMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleMicroserviceApplication.class, args);
    }

}
