package com.wesley.seckill.schedu;

import com.alibaba.fastjson.JSON;
import com.wesley.seckill.constant.Constant;
import com.wesley.seckill.model.SeckillOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @program: superSeckill
 * @description:
 * @author: weili
 * @create: 2018-05-26 20:14
 **/

@Service
@Slf4j
public class SeckillOrderExecutor {



    @Qualifier("clusterRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param
     * @return void
     * @description 定时每秒从redis中获取订单入库
     * @author weili
     * @date 2018/5/26 16:01
     */
    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void submitOrder() {


        String obj = (String) redisTemplate.opsForList().rightPop(Constant.RedisKey.preOrderList, 3, TimeUnit.SECONDS);

        if (StringUtils.isEmpty(obj)) {
            return;
        }

        log.info("订单开始处理");

        SeckillOrder preorder = JSON.parseObject(obj, SeckillOrder.class);


        log.info("订单处理完成: {} ",preorder.getUserPhone());



    }
}
