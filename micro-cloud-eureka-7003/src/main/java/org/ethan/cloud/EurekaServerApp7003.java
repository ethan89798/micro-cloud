package org.ethan.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp7003 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp7003.class, args);
    }
}
