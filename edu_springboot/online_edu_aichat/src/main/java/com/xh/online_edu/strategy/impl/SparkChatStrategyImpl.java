package com.xh.online_edu.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.pojo.Message;
import com.xh.online_edu.util.TokenBucketUtil;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SparkChatStrategyImpl extends BaseChatStrategyImpl{

    @Value("${chat.spark.APIPassword}")
    private String APIPassword;
    private static String url="https://spark-api-open.xf-yun.com/v1/chat/completions";
    private final TokenBucketUtil tokenBucket=new TokenBucketUtil(2,2);

    @Override
    public ChatModel getChatModel() {
        return ChatModel.SPARK;
    }

    @Override
    public Response ChatRequestHandle(List<Message> msgList) throws Exception {
        Request request=new Request.Builder()
                .url(url)
                .method("POST",buildRequestBody(msgList))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+APIPassword)
                .build();
        return super.HTTP_CLIENT.newCall(request).execute();
    }

    @Override
    public void getRequestConsent() throws InterruptedException {
        this.tokenBucket.consume();
    }

    private RequestBody buildRequestBody(List<Message> msgList){
        ConcurrentHashMap<String,Object> map=new ConcurrentHashMap();
        map.put("messages",msgList);
        map.put("stream",true);
        map.put("model","lite");
        map.put("temperature",0.6);
        map.put("max_tokens",1024);
        String json= JSON.toJSONString(map);
        return RequestBody.create(super.mediaType, json);
    }
}
