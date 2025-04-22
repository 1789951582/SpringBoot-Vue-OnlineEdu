package com.xh.online_edu.strategy;

import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.pojo.Message;
import okhttp3.Response;

import java.util.List;

public interface ChatStrategy {

    ChatModel getChatModel();

    String ChatResponseHandle(String json);

    Response ChatRequestHandle(List<Message> msgList) throws Exception;

    void getRequestConsent() throws InterruptedException;

    void releaseRequestConsent();

}
