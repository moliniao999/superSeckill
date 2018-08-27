package com.wesley.seckill.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * @program: superSeckill
 * @description: redis分布式锁实现
 * @author: weili
 * @create: 2018-06-12 10:21
 **/


@Slf4j
@Component
public class RedisLock {




    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 重试时间
     */
    private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 200;
    /**
     * 锁的后缀
     */
    private static final String LOCK_SUFFIX = "_redis_lock_seckill";
    /**
     * 锁的key
     */
    private String lockKey;
    /**
     * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
     */
    private int expireTime = 30 * 1000;
    /**
     * 线程获取锁的等待时间
     */
    private int timeoutMsecs = 1 * 1000;
    /**
     * 是否锁定标志
     */
    private volatile boolean locked = false;

    public boolean isLocked() {
        return locked;
    }



    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;



    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否获取成功
     */
    public  boolean lock(String lockKey, String requestId) {
        String result = jedisCluster.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            locked = true;
            return true;
        }
        return false;

    }

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间（ms）
     * @return 是否获取成功
     */
    public  boolean lock(String lockKey, String requestId, int expireTime) {
        String result = jedisCluster.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            locked = true;
            return true;
        }
        return false;
    }


    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间（ms）
     * @return 是否获取成功
     */
    public  boolean trylock(String lockKey, String requestId, int expireTime) throws InterruptedException {

        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            String result = jedisCluster.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                locked = true;
                return true;
            }

            timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
            // 延时
            Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
        }
        return false;

    }


    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean unlock(String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisCluster.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            locked = false;
            return true;
        }
        log.error("解锁异常,op=start_LikaRedisLock_unlock: [ lockKey={},requestId={} ]", lockKey,requestId);
        return false;

    }



}
