//package com.xh.online_edu.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @Author: XiaoHao
// * @Date: 2025/01/31 21:07
// * @Description:
// */
//
//@Configuration
//public class RedisConfig {
//    // 自己定义了一个 RedisTemplate
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//
//        // 使用 GenericJackson2JsonRedisSerializer 替换 Jackson2JsonRedisSerializer
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        // key 采用 String 的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value 和 hash value 采用 GenericJackson2JsonRedisSerializer
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);
//
//        template.afterPropertiesSet();
//
//        return template;
//    }
//}