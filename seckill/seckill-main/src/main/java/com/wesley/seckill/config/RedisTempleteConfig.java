package com.wesley.seckill.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisTempleteConfig {


    List<String> nodes;


    String password;

    @Bean
    @ConditionalOnMissingBean
    public RedisConnectionFactory connectionFactory() {
        //String[] servers = XConfProperty.get("redis.cluster.hosts").split(",");
        String[] servers = nodes.toArray(new String[nodes.size()]);
        JedisConnectionFactory factory = new JedisConnectionFactory(new RedisClusterConfiguration(Arrays.asList(servers)));
        factory.setPassword(password);
        return factory;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean(name = "clusterRedisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory());
        // 开启事务支持
        template.setEnableTransactionSupport(true);
        // 使用String格式序列化缓存键
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        // 使用json格式序列化缓存值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
