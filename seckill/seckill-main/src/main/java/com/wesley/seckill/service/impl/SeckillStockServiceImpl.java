package com.wesley.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.wesley.seckill.constant.Constant;
import com.wesley.seckill.dao.SeckillStockMapper;
import com.wesley.seckill.model.SeckillStock;
import com.wesley.seckill.service.AbstractService;
import com.wesley.seckill.service.SeckillStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @description:
* @author: weili
* @create: 2018-05-24
*/
@Service
@Slf4j
public class SeckillStockServiceImpl extends AbstractService<SeckillStock> implements SeckillStockService {

    @Autowired
    private SeckillStockMapper seckillStockMapper;

    @Qualifier("clusterRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public int saleStock(Map seckillOrder) {
        return seckillStockMapper.saleStock(seckillOrder);
    }

    @Override
    public Long getStockCount(Integer id) {
        String key = Constant.RedisKey.stockKeyPrifix + id;
        if (redisTemplate.hasKey(key)) {
            String count = redisTemplate.opsForValue().get(key).toString();
            return Long.parseLong(count);
        }
        //从数据库中获取库存
        log.error("从数据库中获取库存,op=start_SeckillStockServiceImpl_getStockCount: [ id={} ]", id);
        SeckillStock stock = super.findById(id);
        redisTemplate.opsForValue().set(key,stock.getStock().toString());
        return stock.getStock().longValue();
    }


    @Override
    public SeckillStock findById(Integer id) {
        String key = Constant.RedisKey.sidKeyPrifix + id;
        if (redisTemplate.hasKey(key)) {
            String jsonStr = redisTemplate.opsForValue().get(key).toString();
            return JSON.parseObject(jsonStr, SeckillStock.class);
        }
        //数据库中获取库存
        log.error("从数据库中获取商品信息,op=start_SeckillStockServiceImpl_getStockCount: [ id={} ]", id);
        SeckillStock stock = super.findById(id);
        redisTemplate.opsForValue().set(key,JSON.toJSONString(stock));
        return stock;
    }
}
