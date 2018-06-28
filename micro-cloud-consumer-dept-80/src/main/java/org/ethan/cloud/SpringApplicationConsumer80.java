package org.ethan.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringApplicationConsumer80 {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationConsumer80.class, args);
    }
}
