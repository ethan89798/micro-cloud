package org.ethan.cloud.service.impl;

import org.ethan.cloud.dao.DeptDao;
import org.ethan.cloud.entries.Dept;
import org.ethan.cloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public List<Dept> findAll() {
        return deptDao.findAll();
    }

    @Override
    public Dept findById(Long id) {
        return deptDao.findById(id);
    }

    @Override
    public boolean addDept(Dept dept) {
        int count = deptDao.addDept(dept);
        if (count > 0) {
            return true;
        }
        return false;
    }
}
