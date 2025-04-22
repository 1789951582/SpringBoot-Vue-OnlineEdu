package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.ChatDto;
import com.xh.online_edu.model.res.ChatRes;
import com.xh.online_edu.pojo.Chat;
import com.xh.online_edu.pojo.Message;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    //创建
    Chat newChat(Long uid)throws StatusFailException;

    //修改
    boolean rename(Long cid, Long uid, String newName) throws StatusFailException;

    //查找
    List<Chat> getHistoryChatListByUid(Long uid);

    //删除
    boolean delChat(Long cid, Long uid) throws StatusFailException;

    //查找对话消息
    List<Message> getChatMsg(Long uid, Long cid) throws StatusFailException;

    //对话
    Flux<String> chat(ChatDto dto);

    Object getchatByQid(Long questionId, Integer type,Long uid) throws StatusFailException;

//    ChatRes getchatByQid(Long questionId, Long uid) throws StatusFailException;



}
