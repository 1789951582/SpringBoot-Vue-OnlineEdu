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

public class GlmChatStrategyImpl extends BaseChatStrategyImpl{

    @Value("${chat.siliconflow.apiKey}")
    private String apiKey;
    private String url="https://api.siliconflow.cn/v1/chat/completions";
    private final TokenBucketUtil tokenBucket=new TokenBucketUtil(1000,16);

    @Override
    public ChatModel getChatModel() {
        return ChatModel.GLM;
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
        map.put("model","THUDM/glm-4-9b-chat");
        map.put("max_tokens",512);
        String json= JSON.toJSONString(map);
        return RequestBody.create(super.mediaType, json);
    }
}
