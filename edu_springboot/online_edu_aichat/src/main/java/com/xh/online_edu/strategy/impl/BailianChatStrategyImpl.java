package com.xh.online_edu.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.pojo.Message;
import com.xh.online_edu.strategy.ChatStrategy;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BailianChatStrategyImpl extends BaseChatStrategyImpl {

    @Value("${chat.bailian.apiKey}")
    private String apiKey;
    private String url="https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    @Override
    public ChatModel getChatModel() {
        return ChatModel.BAILIAN;
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

    private RequestBody buildRequestBody(List<Message> msgList){
        ConcurrentHashMap<String,Object> map=new ConcurrentHashMap();
        map.put("messages",msgList);
        map.put("stream",true);
        map.put("model","qwen2.5-1.5b-instruct");
        map.put("max_tokens",1024);
        map.put("temperature",0.7);
        String json= JSON.toJSONString(map);
        System.out.println(json);
        return RequestBody.create(super.mediaType, json);
    }
}
