package com.xh.online_edu.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.xh.online_edu.strategy.ChatStrategy;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

public abstract class BaseChatStrategyImpl implements ChatStrategy {
    static OkHttpClient HTTP_CLIENT = new OkHttpClient();
    static MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    @Override
    public String ChatResponseHandle(String json){
        return JSON.parseObject(json).getJSONArray("choices").getJSONObject(0).getJSONObject("delta").getString("content");
    }

    @Override
    public void getRequestConsent() throws InterruptedException {
        return;
    }

    @Override
    public void releaseRequestConsent() {
        return;
    }
}
