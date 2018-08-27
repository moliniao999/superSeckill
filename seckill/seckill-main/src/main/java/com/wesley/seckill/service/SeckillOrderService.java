package com.wesley.seckill.service;
import com.wesley.seckill.model.SeckillOrder;

/**
* @description:
* @author: weili
* @create: 2018-05-24
*/

public interface SeckillOrderService extends Service<SeckillOrder> {

    void seckill(SeckillOrder seckillOrder);
}
