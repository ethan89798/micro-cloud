package org.ethan.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient     //启动自动注册到eureka
@EnableDiscoveryClient  //服务发现
@EnableCircuitBreaker   //开启熔断服务
public class DeptProvider8001HystrixApp {

    public static void main(String[] args) {
        SpringApplication.run(DeptProvider8001HystrixApp.class, args);
    }
}
