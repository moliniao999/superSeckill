package com.wesley.seckill.schedu;

import com.alibaba.fastjson.JSON;
import com.wesley.seckill.constant.Constant;
import com.wesley.seckill.model.SeckillStock;
import com.wesley.seckill.service.SeckillStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: superSeckill
 * @description: 秒杀定时任务服务类
 * @author: weili
 * @create: 2018-05-26 15:48
 **/


@Service
@Slf4j
@Order(2)
public class SeckillScheduleService implements ApplicationRunner {


    @Qualifier("clusterRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;



    @Autowired
    private JedisCluster jedisCluster;


    @Resource
    private SeckillStockService stockService;

    /**
     * 启动加载商品库存
     *
     * @param applicationArguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("开始加载秒杀商品");
        log.info(applicationArguments.toString());

     //   jedisCluster.set("test", "212");

        Example query = new Example(SeckillStock.class);
        query.createCriteria().andEqualTo("state", 1);
        List<SeckillStock> stocks = stockService.findByCondition(query);
        String stockKey;
        String sidKey;
        int num = 0;
        if (!CollectionUtils.isEmpty(stocks)) {
            for (SeckillStock stock : stocks) {
                //缓存商品
                sidKey = Constant.RedisKey.sidKeyPrifix + stock.getSeckillId();
                redisTemplate.opsForValue().set(sidKey, JSON.toJSONString(stock));
                //缓存商品库存
                stockKey = Constant.RedisKey.stockKeyPrifix + stock.getSeckillId();
                redisTemplate.opsForValue().set(stockKey, stock.getStock());
                num ++;
            }

        }

        log.info("加载秒杀商品完成: count = {}",num);

    }


    ///**
    // * @param
    // * @return void
    // * @description 定时每秒从redis中获取订单入库
    // * @author weili
    // * @date 2018/5/26 16:01
    // */
    //@Scheduled(initialDelay = 1000, fixedRate = 1000)
    //public void submitOrder() {
    //
    //    log.info("订单开始处理");
    //
    //
    //
    //
    //}


    @Async("threadPoolTaskExecutor")
    public void sendMsg() {

        log.info("异步方法开始执行");


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }


        log.info("异步方法执行完成");

    }



}
