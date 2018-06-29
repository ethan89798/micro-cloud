package org.ethan.cloud.cfg.beans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {

    /**
     * Spring Cloud基于Ribbon客户端的负载均衡
     */
    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 使用配置负载均衡算法替换默认的算法
     * @return
     */
    @Bean
    public IRule getRule() {
        //轮询
//        return new RoundRobinRule();
        //随机
//        return new RandomRule();
        //重试--轮询
        return new RetryRule();
    }
}
