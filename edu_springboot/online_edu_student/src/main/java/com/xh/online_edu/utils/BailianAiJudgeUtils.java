package com.xh.online_edu.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class BailianAiJudgeUtils {
    @Value("${aiJudge.bailian.apiKey}")
    private String apiKey;
    @Value("${aiJudge.bailian.appId}")
    private String appId;

    private final static OkHttpClient client=new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
            .readTimeout(120, TimeUnit.SECONDS)    // 读取超时（长文本需更长时间）
            .build();
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final TokenBucketUtil tokenBucket=new TokenBucketUtil(32,16);
    private static String url;

    @PostConstruct
    public void init(){
        if (this.url==null){
            this.url=String.format("https://dashscope.aliyuncs.com/api/v1/apps/%s/completion", this.appId);
        }
        System.out.println(this.url);
    }

    public Response reqJudge(String question, String answer) throws IOException, InterruptedException {

        //限流
        tokenBucket.consume();

        //构建请求体
        HashMap<String,Map<String,String>> reqBody=new HashMap<>();
        HashMap<String,String> input=new HashMap<>();
        input.put("prompt",String.format("question:%s,studentAnswer:%s", question, answer));

        reqBody.put("input",input);
        reqBody.put("parameters", new HashMap<>()); // 必须存在的空对象
        reqBody.put("debug", new HashMap<>()); // 必须存在的空对象

        String reqbodyStr=objectMapper.writeValueAsString(reqBody);

        RequestBody body= RequestBody.create(reqbodyStr,mediaType);
        Request request=new Request.Builder()
                .url(this.url)
                .method("POST",body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+apiKey)
                .build();

        return client.newCall(request).execute();
    }

}
