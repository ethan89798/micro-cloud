package org.ethan.cloud.controller;

import org.ethan.cloud.entries.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptControllerConsumer {

    private static final String REST_URL_PREFIX = "http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "consumer/dept/add")
    public boolean add(Dept dept) {
        String url = REST_URL_PREFIX + "/dept/add";
        return restTemplate.postForObject(url, dept, Boolean.class);
    }

    @RequestMapping(value = "consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        String url = REST_URL_PREFIX + "/dept/get/" + id;
        return restTemplate.getForObject(url, Dept.class);
    }

    @RequestMapping(value = "consumer/dept/list")
    public List<Dept> list() {
        String url = REST_URL_PREFIX + "/dept/list";
        return restTemplate.getForObject(url, List.class);
    }
}
