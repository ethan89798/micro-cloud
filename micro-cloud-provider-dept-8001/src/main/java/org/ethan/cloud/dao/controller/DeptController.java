package org.ethan.cloud.dao.controller;

import org.ethan.cloud.entries.Dept;
import org.ethan.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping("/dept/add")
    public boolean add(@RequestBody Dept dept) {
        return deptService.addDept(dept);
    }

    @GetMapping("/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return deptService.findById(id);
    }

    @GetMapping("/dept/list")
    public List<Dept> list() {
        List<Dept> list = deptService.findAll();
        return list;
    }
}
