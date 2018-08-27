package com.wesley.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.wesley.seckill.constant.Constant;
import com.wesley.seckill.dao.SeckillOrderMapper;
import com.wesley.seckill.enumeration.BusinessCodeEnum;
import com.wesley.seckill.execption.ServiceException;
import com.wesley.seckill.model.SeckillOrder;
import com.wesley.seckill.model.SeckillStock;
import com.wesley.seckill.service.AbstractService;
import com.wesley.seckill.service.SeckillOrderService;
import com.wesley.seckill.service.SeckillStockService;
import com.wesley.seckill.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: weili
 * @create: 2018-05-24
 */
@Service
@Slf4j
public class SeckillOrderServiceImpl extends AbstractService<SeckillOrder> implements SeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;


    @Resource
    private SeckillStockService stockService;


    @Qualifier("clusterRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;



    @Resource
    private JedisCluster jedisCluster;


    @Override
    public void seckill(SeckillOrder seckillOrder) {

        //检查秒杀商品信息
        SeckillStock stock = stockService.findById(seckillOrder.getSeckillId());
        if (stock == null) {
            log.error("商品不存在,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
            throw new ServiceException(BusinessCodeEnum.STOCK_NOTNULL);
        }
        //商品最大购买数量检查
        if (seckillOrder.getGoodsNum() > stock.getMaxBuy()) {
            log.info("超出商品最大购买数量,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
            throw new ServiceException(BusinessCodeEnum.STOCK_MAXBUY);
        }

        //检查商品状态：是否在秒杀中

        //检查库存
        Long stockCount = stockService.getStockCount(seckillOrder.getSeckillId());
        if (stockCount <= 0) {
            log.info("已售完,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
            throw new ServiceException(BusinessCodeEnum.STOCK_EMPTY);
        }


        //TODO 判断一个账户同商品只能秒杀一次,需要加锁
        //从redis获取购买记录,用于校验一个账户同商品只能秒杀一次
        //String orderKey = Constant.RedisKey.orderKeyPrifix + seckillOrder.getUserPhone()+"_"+seckillOrder.getSeckillId();
        //if (redisTemplate.hasKey(orderKey)) {
        //    log.info("一个账户同商品只能秒杀一次,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
        //    throw new ServiceException(BusinessCodeEnum.UNIQUE_USER_SECKILL);
        //}
        //SeckillOrder query = new SeckillOrder();
        //query.setUserPhone(seckillOrder.getUserPhone());
        //query.setSeckillId(seckillOrder.getSeckillId());
        //List<SeckillOrder> orders = seckillOrderMapper.select(query);
        //if (!CollectionUtils.isEmpty(orders)) {
        //    log.info("一个账户同商品只能秒杀一次,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
        //    throw new ServiceException(BusinessCodeEnum.UNIQUE_USER_SECKILL);
        //}

        //扣减库存
      //  redisTemplate.multi();
        String key = Constant.RedisKey.stockKeyPrifix + seckillOrder.getSeckillId();
        stockCount = redisTemplate.opsForValue().increment(key, -seckillOrder.getGoodsNum());
        log.info("扣减库存成功:phone = {}, stockCount = {}",seckillOrder.getUserPhone(),stockCount);
        if (stockCount < 0) {
            log.info("库存不足,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
            throw new ServiceException(BusinessCodeEnum.STOCK_LESS);
        }

        //加入预占库存列表
        redisTemplate.opsForList().leftPush(Constant.RedisKey.preOrderList, JSON.toJSONString(seckillOrder));

//        redisTemplate.exec();

        //清除商品缓存
        //key = Constant.RedisKey.sidKeyPrifix + seckillOrder.getSeckillId();
        //redisTemplate.delete(key);

        //同步库存到数据库
        Map map = new HashMap();
        map.put("version", stock.getVersion());
        map.put("goodsNum", seckillOrder.getGoodsNum());
        map.put("seckillId", stock.getSeckillId());
        int effect = stockService.saleStock(map);
        if (effect == 0) {
            log.error("更新异常,服务器忙:op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
            throw new ServiceException(BusinessCodeEnum.SERVER_BUSY);
        }


        //下单入库
        Long orderId = Long.parseLong(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss") + String.valueOf(new Random().nextInt(9000) + 1000));
        seckillOrder.setOrderid(orderId);
        seckillOrder.setApplyTime(new Date());
        seckillOrder.setState((byte) 1);
        seckillOrderMapper.insertSelective(seckillOrder);

        log.info("下单成功,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
        //购买记录存入redis
        //redisTemplate.opsForValue().set(orderKey, orderId);
        //redisTemplate.expire(orderKey, 30, TimeUnit.MICROSECONDS);


    }


}
