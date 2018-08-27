package com.wesley.seckill;

import com.wesley.seckill.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import java.util.*;

@org.junit.runner.RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
//@ContextConfiguration(initializers = {TestApplicationContextInitializer.class})
@Slf4j
public class SeckillTest {




    @Qualifier("clusterRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private JedisCluster jedisCluster;


    @Test
    public void testRedisOP() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "aaa");
        map.put("2", "bbb");
        map.put("3", "ccc");
        String key = "test";
        redisTemplate.opsForHash().putAll(key, map);

        map.forEach((k,v)->{
            log.info(k + ":" +redisTemplate.opsForHash().get(key,k));
        });




        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb.append("    if (stock == -1) then");
        sb.append("        return 1;");
        sb.append("    end;");
        sb.append("    if (stock > 0) then");
        sb.append("        local ss = redis.call('incrby', KEYS[1], -1);");
        sb.append("        redis.call('set', KEYS[2], ARGV[1]); ");
        sb.append("        return stock;");
        sb.append("    end;");
        sb.append("    return 0;");
        sb.append("end;");
        sb.append("return -1;");
        String STOCK_LUA = sb.toString();

        String stockkey = Constant.RedisKey.stockKeyPrifix + 9527;

        jedisCluster.set(stockkey, "10");//初始化库存


        String keyorder = Constant.RedisKey.preOrderList;

        long num = -3;
        //long stockCount = redisTemplate.opsForValue().increment(key, num);
        //if (stockCount < 0) {
        //    log.info("库存不足,op=start_SeckillOrderServiceImpl_seckill: [ seckillOrder={} ]", seckillOrder);
        //    throw new ServiceException(BusinessCodeEnum.STOCK_LESS);
        //}
        List keyList = Arrays.asList(stockkey);
        List valuelist = Arrays.asList(num);
        //加入预占库存列表
       // redisTemplate.opsForList().leftPush(Constant.RedisKey.preOrderList, JSON.toJSONString(seckillOrder));

       // Object result = jedisCluster.eval(STOCK_LUA, keyList, valuelist);
        Object result = jedisCluster.eval(STOCK_LUA, keyList, Collections.singletonList(num+""));

        log.info("result = {}",result.toString());
        if (result.equals( -1)) {
            log.info("key不存在");
        }
    }



}

