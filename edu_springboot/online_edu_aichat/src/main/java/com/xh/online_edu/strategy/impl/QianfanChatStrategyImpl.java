package com.xh.online_edu.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.pojo.Message;
import com.xh.online_edu.util.TokenBucketUtil;
import jakarta.annotation.PostConstruct;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class QianfanChatStrategyImpl extends BaseChatStrategyImpl{

    @Value("${chat.qianfan.apiKey}")
    private String apiKey;
    @Value("${chat.qianfan.secretKey}")
    private String secretKey ;
    private static String url;
    private final TokenBucketUtil tokenBucket=new TokenBucketUtil(300,5);

    @PostConstruct
    public void init() throws IOException {
        if (url==null){
            this.getUrl();
        }
    }

    @Override
    public ChatModel getChatModel() {
        return ChatModel.QIANFAN;
    }

    @Override
    public Response ChatRequestHandle(List<Message> msgList) throws Exception {
        Request request=new Request.Builder()
                .url(url)
                .method("POST",buildRequestBody(msgList))
                .addHeader("Content-Type", "application/json")
                .build();
        return super.HTTP_CLIENT.newCall(request).execute();
    }

    private RequestBody buildRequestBody(List<Message> msgList){
        ConcurrentHashMap<String,Object> map=new ConcurrentHashMap();
        map.put("messages",msgList);
        map.put("stream",true);
        map.put("temperature",0.7);
        map.put("penalty_score",1.2);
        String json= JSON.toJSONString(map);
        return RequestBody.create(super.mediaType, json);
    }

    @Scheduled(cron = "0 0 3 1,15 * ?")
    private void getUrl() throws IOException {
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey);
        Request request=new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST",body)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .build();
        Response response=HTTP_CLIENT.newCall(request).execute();
        String token =JSONObject.parseObject(response.body().string()).getString("access_token");
        HttpUrl.Builder urlBuilder=HttpUrl.parse(
                "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant"
        ).newBuilder();
        urlBuilder.addQueryParameter("access_token",token);
        this.url=urlBuilder.build().toString();
    }

    public String ChatResponseHandle(String json){
        return JSON.parseObject(json).getString("result");
    }

    @Override
    public void getRequestConsent() throws InterruptedException {
        this.tokenBucket.consume();
    }

    @Override
    public void releaseRequestConsent() {
        return;
    }
}
