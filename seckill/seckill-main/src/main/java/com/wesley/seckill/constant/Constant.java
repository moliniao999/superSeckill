package com.wesley.seckill.constant;

/**
 * @program: superSeckill
 * @description: 常量类
 * @author: weili
 * @create: 2018-05-26 16:40
 **/
public interface Constant {

    final String appPrefix = "seckill_";

    interface RedisKey{

        String sidKeyPrifix = appPrefix+"key_sid_";//缓存商品
        String stockKeyPrifix = appPrefix+"key_stock_";//缓存商品库存
        String orderKeyPrifix = appPrefix+"key_order_";//缓存订单


        String preOrderList = appPrefix+"key_order_list";//预占库存列表

    }


}
