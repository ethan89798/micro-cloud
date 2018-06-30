package org.ethan.rule;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRule {

    @Bean
    public IRule getRule() {
        /**
         * 测试是否使用我们自定义的负载均衡算法,然后再去实现自定义的算法
         * 因为我们在配置使用了RetryRule(),所以理论时是轮询算法,
         * 如果改为随机算法,那么这个自定义算法是生效的
         */
//        return new RandomRule();
        return new CustomRoundRobinRule();
    }
}
