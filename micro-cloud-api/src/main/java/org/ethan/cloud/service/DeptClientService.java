package org.ethan.cloud.service;

import org.ethan.cloud.entries.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "MICRO-CLOUD-DEPT")
public interface DeptClientService {

    @RequestMapping(value = "/dept/add")
    boolean add(Dept dept);

    @RequestMapping(value = "/dept/get/{id}")
    Dept get(@PathVariable("id") Long id);

    @RequestMapping(value = "/dept/list")
    List<Dept> list();
}
