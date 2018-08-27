package com.wesley.seckill.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wesley.seckill.enumeration.BusinessCodeEnum;
import com.wesley.seckill.enumeration.CodeEnum;
import com.wesley.seckill.execption.ServiceException;
import com.wesley.seckill.model.SeckillOrder;
import com.wesley.seckill.service.LimiterService;
import com.wesley.seckill.service.SeckillOrderService;
import com.wesley.seckill.util.page.ReturnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
* @description:
* @author: weili
* @create: 2018-05-24
*/
@Api(value = "SeckillOrderController",description = "秒杀下单")
@RestController
@RequestMapping("/seckillOrder/")
@Slf4j
public class SeckillOrderController {

    @Autowired
    SeckillOrderService seckillOrderService;


    @Resource
    LimiterService limiterService;

    @PostMapping("add")
    public String add(SeckillOrder seckillOrder) {
        seckillOrderService.save(seckillOrder);
        return "";
    }

    @GetMapping("delete")
    public String delete(@RequestParam Integer id) {
	    seckillOrderService.deleteById(id);
	    return "";
    }

    @PostMapping("update")
    public String update(SeckillOrder seckillOrder) {
	    seckillOrderService.update(seckillOrder);
	    return "";
    }

    @GetMapping("detail")
    public String detail(@RequestParam Integer id) {
        SeckillOrder seckillOrder = seckillOrderService.findById(id);
        return seckillOrder.toString();
    }

    @GetMapping("list")
    public String list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
        PageHelper.startPage(page, size);
        List<SeckillOrder> list = seckillOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return list.toString();
    }


    @ApiOperation(value = "秒杀下单")
    @PostMapping("seckill")
    public ReturnInfo<String> seckill(@RequestBody SeckillOrder seckillOrder) {
        try {
            //未获取令牌直接返回
            if (!limiterService.tryAcquire()) {
                log.warn("服务器忙:op=start_SeckillOrderController_seckill: [ phone={} ]", seckillOrder.getUserPhone());
                return ReturnInfo.create(BusinessCodeEnum.SERVER_BUSY.getCode(),BusinessCodeEnum.SERVER_BUSY.getMsg(),null,null);
            }
            seckillOrderService.seckill(seckillOrder);
            return ReturnInfo.createReturnSuccessOne("秒杀成功");
        } catch (ServiceException e) {
            log.info("秒杀商品失败,业务异常:op=start_SeckillOrderController_seckill: [ seckillOrder={} ]", seckillOrder,e);
            return ReturnInfo.create(e.getCode(),e.getMsg());
        } catch (Exception e) {
            log.error("秒杀商品失败,系统异常:op=start_SeckillOrderController_seckill: [ seckillOrder={} ]", seckillOrder, e);
            return ReturnInfo.create(CodeEnum.SERVER_ERROR);
        }

    }


}
