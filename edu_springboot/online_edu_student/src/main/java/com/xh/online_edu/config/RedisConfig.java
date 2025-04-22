//package com.xh.online_edu.config;
//
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
//import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
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
//
////        ObjectMapper customMapper = new ObjectMapper();
////
////        // 允许解析其他模块的类
////        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
////                .allowIfBaseType("com.xh.online_edu.model.res.TaskRes")
////                .allowIfBaseType("com.xh.online_edu_teacher.model.resData.TaskData")
////                .allowIfSubType("java.util.ArrayList")
////                .build();
////
////        customMapper.activateDefaultTyping(
////                ptv,
////                ObjectMapper.DefaultTyping.NON_FINAL,
////                JsonTypeInfo.As.PROPERTY
////        );
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
//
////@Configuration
////public class RedisConfig {
////
////    @Bean(name = "redisTemplate")
////    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
////        RedisTemplate<String, Object> template = new RedisTemplate<>();
////        template.setConnectionFactory(factory);
////
////        // Json序列化配置
////        ObjectMapper om = new ObjectMapper()
////                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
////                .activateDefaultTyping(
////                        LaissezFaireSubTypeValidator.instance,
////                        ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE
////                );
////
////        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
////        jackson2JsonRedisSerializer.setObjectMapper(om);
////
////        // String 的序列化
////        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
////
////        // 设置序列化方式
////        template.setKeySerializer(stringRedisSerializer);
////        template.setValueSerializer(jackson2JsonRedisSerializer);
////        template.setHashKeySerializer(stringRedisSerializer);
////        template.setHashValueSerializer(jackson2JsonRedisSerializer);
////
////        // 确保在序列化器设置完成后调用 afterPropertiesSet
////        template.afterPropertiesSet();
////
////        return template;
////    }
////}