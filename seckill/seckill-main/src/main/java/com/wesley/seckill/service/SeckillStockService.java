package com.wesley.seckill.service;
import com.wesley.seckill.model.SeckillStock;

import java.util.Map;

/**
* @description:
* @author: weili
* @create: 2018-05-24
*/

public interface SeckillStockService extends Service<SeckillStock> {

    int saleStock(Map seckillOrder);

    Long getStockCount(Integer seckillId);
}
