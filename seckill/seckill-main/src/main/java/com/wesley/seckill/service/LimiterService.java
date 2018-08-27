package com.wesley.seckill.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: superSeckill
 * @description: 令牌桶限流Service
 * @author: weili
 * @create: 2018-05-31 09:44
 **/


@Service
@Slf4j
public class LimiterService {

    //每秒只发出50个令牌
    RateLimiter rateLimiter = RateLimiter.create(50.0);
    /**
     * 尝试获取令牌
     *
     * @return
     */
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }

}
