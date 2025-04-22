//package com.xh.online_edu_teacher.utils;
//
//import ch.qos.logback.core.net.server.Client;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.apis.ClientConfiguration;
//import org.apache.rocketmq.client.apis.ClientServiceProvider;
//import org.apache.rocketmq.client.apis.message.Message;
//import org.apache.rocketmq.client.apis.producer.Producer;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//public class CacheDeleteUtils implements Runnable{
//    private final String key;
//    private final long delayMs;
//    @Autowired
//    RedisUtils redisUtils;
//    @Autowired
//    RocketMQTemplate rocketMQTemplate;
//
//    public CacheDeleteUtils(String key,long delayMs) {
//        this.key=key;
//        this.delayMs=delayMs;
//    }
//
//    @SneakyThrows
//    @Override
//    public void run() {
//        try{
//            Thread.sleep(delayMs);
//            redisUtils.del(key);
//        }catch (Exception e){
//            ClientServiceProvider provider=ClientServiceProvider.loadService();
//            ClientConfiguration config = ClientConfiguration.newBuilder()
//                    .setEndpoints("localhost:8081") // NameServer地址
//                    .build();
//            Producer producer = provider.newProducerBuilder()
//                    .setClientConfiguration(config)
//                    .setTopics("CACHE_DELETE_RETRY")
//                    .build();
//            Message message = provider.newMessageBuilder()
//                    .setTopic("CACHE_DELETE_RETRY")
//                    .setBody(key.getBytes())
//                    .setDeliveryTimestamp(System.currentTimeMillis() + 10_000) // 延迟10秒
//                    .build();
//            rocketMQTemplate.syncSend()
//        }
//    }
//}
