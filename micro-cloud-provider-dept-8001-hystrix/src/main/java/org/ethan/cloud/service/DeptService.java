package org.ethan.cloud.service;

import org.ethan.cloud.entries.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> findAll();

    Dept findById(Long id);

    boolean addDept(Dept dept);
}
