package com.xh.online_edu.strategy.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.pojo.Message;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class HunyuanChatStrategyImpl extends BaseChatStrategyImpl{

    @Value("${chat.hunyuan.secretId}")
    private String secretId;
    @Value("${chat.hunyuan.secretKey}")
    private String secretKey;
    private static String url="https://hunyuan.tencentcloudapi.com";
    private static String SignedHeaders="content-type;host;x-tc-action";
    private static String CanonicalRequestTemp="POST" + '\n' +
            "/" + '\n' +
            "" + '\n' +
            "content-type:application/json; charset=utf-8\nhost:hunyuan.tencentcloudapi.com\nx-tc-action:chatcompletions\n" + '\n' +
            SignedHeaders;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String Authorization;
    private String RequestTimestamp;
    private final Semaphore semaphore=new Semaphore(5);

    @Override
    public ChatModel getChatModel() {
        return ChatModel.HUNYUAN;
    }

    @Override
    public Response ChatRequestHandle(List<Message> msgList) throws Exception {
        Request request=new Request.Builder()
                .url(url)
                .method("POST",buildRequestBody(msgList))
                .addHeader("X-TC-Version","2023-09-01")
                .addHeader("X-TC-Timestamp",this.RequestTimestamp)
                .addHeader("X-TC-Region","ap-guangzhou")
                .addHeader("X-TC-Language","zh-CN")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Authorization",this.Authorization)
                .addHeader("X-TC-RequestClient","APIExplorer")
                .addHeader("Host","hunyuan.tencentcloudapi.com")
                .addHeader("X-TC-Action","ChatCompletions")
                .build();
        return super.HTTP_CLIENT.newCall(request).execute();
    }

    private RequestBody buildRequestBody(List<Message> msgList) throws NoSuchAlgorithmException, InvalidKeyException {
        Map<String,Object> map=new LinkedHashMap();
        map.put("Model","hunyuan-lite");
        map.put("Messages",msgList);
        map.put("Stream",true);
        String json= JSON.toJSONString(map, JSONWriter.Feature.FieldBased);
        json=json.replace("\"role\":","\"Role\":").replace("\"content\":","\"Content\":");
        getAuthentication(json);
        return RequestBody.create(super.mediaType, json);
    }

    private void getAuthentication(String msgListJson) throws InvalidKeyException, NoSuchAlgorithmException {
//        第一步
        String HashedRequestPayload = sha256Hex(msgListJson);
        String CanonicalRequest = this.CanonicalRequestTemp + '\n' + HashedRequestPayload;
//        System.out.println("CanonicalRequest:" + CanonicalRequest);

//        第二步
        String Algorithm = "TC3-HMAC-SHA256";
        Instant instant = Instant.now();
        this.RequestTimestamp = instant.getEpochSecond() + "";
        String date = instant.atZone(ZoneOffset.UTC).toLocalDateTime().format(formatter);
        String CredentialScope = date + "/hunyuan/tc3_request";
        String HashedCanonicalRequest = sha256Hex(CanonicalRequest);
        String StringToSign =
                Algorithm + "\n" +
                        RequestTimestamp + "\n" +
                        CredentialScope + "\n" +
                        HashedCanonicalRequest;
//        System.out.println("StringToSign:" + StringToSign);

//        第三步
        byte[] SecretDate = hmacSHA256("TC3" + secretKey, date);
        byte[] SecretService = hmacSHA256(SecretDate, "hunyuan");
        byte[] SecretSigning = hmacSHA256(SecretService, "tc3_request");
        byte[] signatureBytes = hmacSHA256(SecretSigning, StringToSign);
        String signature = bytesToHex(signatureBytes);
//        System.out.println(signature);
        this.Authorization =
                Algorithm + " " +
                        "Credential=" + this.secretId + "/" + CredentialScope + ", " +
                        "SignedHeaders=" + SignedHeaders + ", " +
                        "Signature=" + signature;
//        System.out.println("Authorization:" + Authorization);
    }


    private String sha256Hex(String input) {
        try {
            // 计算SHA-256哈希值
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 转换为小写
            return hexString.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] hmacSHA256(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    private static byte[] hmacSHA256(byte[] key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public String ChatResponseHandle(String json){
        return JSON.parseObject(json).getJSONArray("Choices").getJSONObject(0).getJSONObject("Delta").getString("Content");
    }

    @Override
    public void getRequestConsent() throws InterruptedException {
        this.semaphore.acquire();
    }

    @Override
    public void releaseRequestConsent() {
        this.semaphore.release();
    }
}
