package org.ethan.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这个规则算法类必须定义为一个类,使用匿名内部类是不能成功使用这个负载均衡算法的
 * @author Ethan Huang
 * @since 2018/06/30
 */
public class CustomRoundRobinRule extends AbstractLoadBalancerRule {

    private static Logger log = LoggerFactory.getLogger(CustomRoundRobinRule.class);

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
                continue;
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
//        for (;;) {
//            int current = nextServerCyclicCounter.get();
//            int next = (current + 1) % modulo;
//            if (nextServerCyclicCounter.compareAndSet(current, next))
//                return next;
//        }
        int count = currentServerRequestCounter.getAndIncrement();
        if (count < 5) {
            return nextServerCyclicCounter.get();
        } else {
            currentServerRequestCounter.set(0);
            int index = nextServerCyclicCounter.incrementAndGet();
            if (index >= modulo) {
                index = 0;
                nextServerCyclicCounter.set(0);
            }
            return index;
        }
    }

    @Override
    public Server choose(Object o) {
        return choose(getLoadBalancer(), o);
    }
}
