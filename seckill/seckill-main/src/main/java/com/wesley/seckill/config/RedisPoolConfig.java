package com.wesley.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 *
 * @Time 2017-08-22
 */
@ConfigurationProperties("redis.pool")
@Component("redisPoolConfig")
@Order(10)
@Slf4j
public class RedisPoolConfig extends JedisPoolConfig {

    //@Value("${spring.redis.host}")
    //private String host;
    //
    //@Value("${spring.redis.port}")
    //private int port;
    //
    //@Value("${spring.redis.timeout}")
    //private int timeout;
    //
    //@Value("${spring.redis.pool.max-idle}")
    //private int maxIdle;
    //
    //@Value("${spring.redis.pool.max-wait}")
    //private long maxWaitMillis;
    //
    //@Value("${spring.redis.password}")
    //private String password;
    //
    //@Bean
    //public JedisPool redisPoolFactory() {
    //    log.info("JedisPool注入成功！！");
    //    log.info("redis地址：" + host + ":" + port);
    //    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    //    jedisPoolConfig.setMaxIdle(maxIdle);
    //    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
    //
    //    JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
    //    //   JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);   //无密码情况
    //    return jedisPool;
    //}


}
