package com.wesley.seckill.sdk.service;

import com.wesley.seckill.sdk.page.ReturnInfo;
import com.wesley.seckill.sdk.vo.SeckillOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: superSeckill
 * @description:
 * @author: weili
 * @create: 2018-06-06 19:18
 **/


//@FeignClient(name = "seckill/order")
//@RequestMapping(path = "/seckill/order")
public interface SeckillOrderService {


    @PostMapping("seckill")
    ReturnInfo<String> seckill(@RequestBody SeckillOrder seckillOrder);

}