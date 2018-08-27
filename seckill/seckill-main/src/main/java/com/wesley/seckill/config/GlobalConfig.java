package com.wesley.seckill.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class GlobalConfig {

    @Autowired
    ApplicationContext applicationContext;

    private static final Logger LOG = LoggerFactory.getLogger(GlobalConfig.class);

    private final static int DEFAULT_TIMEOUT = 5000;

    private final static int DEFAULT_MAX_ATTEMPTS = 6;


    List<String> nodes;

    String password;

    @Bean
    @ConditionalOnMissingBean
    public JedisCluster jedisCLuster(
            @Qualifier("redisPoolConfig") RedisPoolConfig redisPoolConfig) {
        String[] servers = nodes.toArray(new String[nodes.size()]);
        Set<HostAndPort> rnodes = new HashSet<>();

        for (String ipPort : servers) {
            String[] ipPortPair = ipPort.split(":");
            rnodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        if (StringUtils.isEmpty(password)) {
            return new JedisCluster(rnodes);
        }
        return new JedisCluster(rnodes, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_MAX_ATTEMPTS,
                password, redisPoolConfig);
    }

    /*
    * 重写ribbon的超时时间，原默认为1s，改写后变为10s
    * */
    //@Bean
    //@ConditionalOnMissingBean
    //public IClientConfig ribbonClientConfig() {
    //    DefaultClientConfigImpl config = new DefaultClientConfigImpl();
    //    config.loadProperties(AppConstants.APP_SERVICE_NAME);
    //    config.set(CommonClientConfigKey.ConnectTimeout, SystemConstants.CONNECT_TIMEOUT);
    //    config.set(CommonClientConfigKey.ReadTimeout, SystemConstants.READ_TIMEOUT);
    //    config.set(CommonClientConfigKey.OkToRetryOnAllOperations, false);
    //    return config;
    //}


    /*
    * 异步调用全局线程池
    * */
    @Bean(value = "threadPoolTaskExecutor")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(1000);
        threadPoolTaskExecutor.setThreadNamePrefix("order-async-");
        threadPoolTaskExecutor.setQueueCapacity(0);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setCorePoolSize(20);
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        return threadPoolTaskExecutor;
    }





    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
