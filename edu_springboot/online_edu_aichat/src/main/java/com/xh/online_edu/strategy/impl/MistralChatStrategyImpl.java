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

public class MistralChatStrategyImpl extends BaseChatStrategyImpl{

    @Value("${chat.volcengine.apiKey}")
    private String apiKey;
    private String url="https://ark.cn-beijing.volces.com/api/v3/chat/completions";
    private final TokenBucketUtil tokenBucket=new TokenBucketUtil(60,1);

    @Override
    public ChatModel getChatModel() {
        return ChatModel.MISTRAL;
    }

    @Override
    public Response ChatRequestHandle(List<Message> msgList) throws Exception {
        Request request=new Request.Builder()
                .url(url)
                .method("POST",buildRequestBody(msgList))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+apiKey)
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
        map.put("model","ep-20240928010146-frznz");
        map.put("max_tokens",1024);
        String json= JSON.toJSONString(map);
        return RequestBody.create(super.mediaType, json);
    }
}
