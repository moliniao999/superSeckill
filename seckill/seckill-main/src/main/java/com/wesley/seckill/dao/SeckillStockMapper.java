package com.wesley.seckill.dao;

import com.wesley.seckill.model.SeckillStock;
import com.wesley.seckill.util.MyMapper;

import java.util.Map;

public interface SeckillStockMapper extends MyMapper<SeckillStock> {
    int saleStock(Map seckillOrder);
}