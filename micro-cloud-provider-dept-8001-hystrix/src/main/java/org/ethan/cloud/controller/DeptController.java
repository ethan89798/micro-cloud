package org.ethan.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.ethan.cloud.entries.Dept;
import org.ethan.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private DeptService deptService;

    /**
     * 如果不想每个方法都使用这个熔断注解,可以在客户端进行aop的编程
     * 重写一个类实现FallbackFactory<>然后重写返回方法
     * 请参考api-->DeptClientFallbackFactory
     * @param dept
     * @return
     */
    @PostMapping("/dept/add")
    @HystrixCommand(fallbackMethod = "fallBackAdd")
    public boolean add(@RequestBody Dept dept) {
        return deptService.addDept(dept);
    }

    public boolean fallBackAdd(@RequestBody Dept dept) {
        return false;
    }

    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "fallBackGet")
    public Dept get(@PathVariable("id") Long id) {
        Dept dept = deptService.findById(id);
        if (dept == null) {
            throw new RuntimeException();
        }
        return dept;
    }

    public Dept fallBackGet(@PathVariable("id") Long id) {
        return new Dept(id, "没有这个值", "db");
    }

    @GetMapping("/dept/list")
    @HystrixCommand(fallbackMethod = "fallBackList")
    public List<Dept> list() {
        List<Dept> list = deptService.findAll();
        return list;
    }

    public List<Dept> fallBackList() {
        return Collections.emptyList();
    }
}
