package org.ethan.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class CustomRule {

    private static Logger log = LoggerFactory.getLogger(CustomRule.class);

    @Bean
    public IRule getRule() {
        /**
         * 测试是否使用我们自定义的负载均衡算法,然后再去实现自定义的算法
         * 因为我们在配置使用了RetryRule(),所以理论时是轮询算法,
         * 如果改为随机算法,那么这个自定义算法是生效的
         */
//        return new RandomRule();
        return new AbstractLoadBalancerRule() {

            /**
             * 最大的获取次数,防止所有服务没有导致获取阻塞
             */
            private static final int MAX_CHOOSE_COUNT = 10;
            private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);
            private AtomicInteger currentServerRequestCounter = new AtomicInteger(0);

            @Override
            public void initWithNiwsConfig(IClientConfig iClientConfig) {

            }

            private Server choose(ILoadBalancer lb, Object key) {
                if (lb == null) {
                    log.warn("no load balancer");
                    return null;
                }

                Server server = null;
                int count = 0;

                while (server == null && count++ < MAX_CHOOSE_COUNT) {

                    List<Server> reachableServers = lb.getReachableServers();
                    List<Server> allServers = lb.getAllServers();

                    int reachCount = reachableServers.size();
                    int totalCount = allServers.size();

                    if (reachCount == 0 || totalCount == 0) {
                        log.warn("No up servers available from load balancer: " + lb);
                        return null;
                    }

                    int nextServerIndex = incrementAndGetModulo(totalCount);
                    server = allServers.get(nextServerIndex);

                    if (server == null) {
                        Thread.yield();
                        return null;
                    }

                    if (server.isAlive() && server.isReadyToServe()) {
                        return server;
                    }

                    server = null;
                }

                if (count >= MAX_CHOOSE_COUNT) {
                    log.warn("No available alive servers after 10 tries from load balancer: " + lb);
                }
                return server;
            }

            private int incrementAndGetModulo(int modulo) {
                for (; ; ) {
                    int count = currentServerRequestCounter.get();
                    if (count < 5) {
                        currentServerRequestCounter.incrementAndGet();
                        return nextServerCyclicCounter.get();
                    } else {
                        int index = nextServerCyclicCounter.incrementAndGet();
                        if (index > modulo) {
                            index = 0;
                            nextServerCyclicCounter.set(0);
                        }
                        return index;
                    }
                }
            }

            @Override
            public Server choose(Object o) {
                return choose(getLoadBalancer(), o);
            }
        };
    }
}
