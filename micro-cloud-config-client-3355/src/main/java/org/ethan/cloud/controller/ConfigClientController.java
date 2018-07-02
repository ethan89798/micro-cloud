package org.ethan.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaZoneUrl;
    @Value("${server.port}")
    private String port;

    @GetMapping("config")
    public String config() {
        StringBuilder sb = new StringBuilder();
        sb.append("applicationName=" + applicationName);
        sb.append("eurekaUrl=" + eurekaZoneUrl);
        sb.append("serverPort=" + port);
        System.out.println(sb.toString());
        return sb.toString();
    }
}
