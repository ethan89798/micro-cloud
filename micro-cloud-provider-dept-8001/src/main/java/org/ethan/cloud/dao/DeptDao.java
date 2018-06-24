package org.ethan.cloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.ethan.cloud.entries.Dept;

import java.util.List;

@Mapper
public interface DeptDao {

    int addDept(Dept dept);

    Dept findById(Long id);

    List<Dept> findAll();
}
