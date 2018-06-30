package org.ethan.cloud.service;

import feign.hystrix.FallbackFactory;
import org.ethan.cloud.entries.Dept;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DeptClientFallbackFactory implements FallbackFactory<DeptClientService> {

    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public boolean add(Dept dept) {
                return false;
            }

            @Override
            public Dept get(Long id) {
                return new Dept(id, "服务暂停", "数据库连接失败");
            }

            @Override
            public List<Dept> list() {
                return Collections.emptyList();
            }
        };
    }
}
