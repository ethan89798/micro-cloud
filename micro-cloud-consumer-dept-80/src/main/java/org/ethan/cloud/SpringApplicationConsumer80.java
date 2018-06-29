package org.ethan.cloud;

import org.ethan.rule.CustomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "MICRO-CLOUD-DEPT", configuration = CustomRule.class)
public class SpringApplicationConsumer80 {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationConsumer80.class, args);
    }
}
