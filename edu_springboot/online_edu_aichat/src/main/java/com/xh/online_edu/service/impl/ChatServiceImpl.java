package com.xh.online_edu.service.impl;

import com.alibaba.fastjson2.JSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yitter.idgen.YitIdHelper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.ChatMapper;
import com.xh.online_edu.mapper.MessageMapper;
import com.xh.online_edu.model.dto.ChatDto;
import com.xh.online_edu.model.enums.ChatModel;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.Chat;
import com.xh.online_edu.pojo.Message;
import com.xh.online_edu.pojo.po.question.*;
import com.xh.online_edu.service.ChatService;
import com.xh.online_edu.strategy.ChatStrategy;
import com.xh.online_edu.strategy.QuestionCoreStrategy;
import com.xh.online_edu.strategy.factory.ChatStrategyFactory;
import com.xh.online_edu.strategy.factory.QuestionStrategyFactory;
import com.xh.online_edu.util.SystemMsgUtil;
import com.xh.online_edu.utils.RedisUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatMapper chatMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    ChatStrategyFactory chatStrategyFactory;
    @Autowired
    QuestionStrategyFactory questionStrategyFactory;
    @Autowired
    SystemMsgUtil systemMsgUtil;

    //创建对话
    @Override
    public Chat newChat(Long uid) throws StatusFailException {
        Chat chat=new Chat(YitIdHelper.nextId(),uid);
        //存储新创建的chat到mysql
        int isOk=chatMapper.insert(chat);
        if (isOk<0){
            throw new StatusFailException("创建失败");
        }
        return chat;
    }

    //修改对话名
    @Override
    public boolean rename(Long cid, Long uid, String newName) throws StatusFailException {
        int isOk=chatMapper.rename(cid,uid,newName);
        if (isOk<0){
            throw new StatusFailException("修改失败");
        }
        return true;
    }

    //获取历史对话
    @Override
    public List<Chat> getHistoryChatListByUid(Long uid) {
        return chatMapper.selectByUid(uid);
    }

    //删除对话
    @Override
    public boolean delChat(Long cid, Long uid) throws StatusFailException {
        int isOk=chatMapper.deleteByCIdAndUid(cid,uid);
//        messageMapper.delete()
        if (isOk<0){
            throw new StatusFailException("删除失败");
        }
        return true;
    }

    //获取对话消息
    @Override
    public List<Message> getChatMsg(Long uid, Long cid) throws StatusFailException {
        List<Message> msgList= redisUtils.getList("chatId:"+cid+"MsgList",Message.class);
        if (msgList.size()==0){
            msgList=messageMapper.selectByCid(cid);
            if (msgList.size()!=0){
                redisUtils.pushListAll("chatId:"+cid+"MsgList",msgList,1800);
            }
        }
        return msgList;
    }

    @Override
    public Flux<String> chat(ChatDto dto) {
        try {
            //获取策略
            ChatModel model=ChatModel.of(dto.getModel());
            ChatStrategy chatStrategy=chatStrategyFactory.getStrategy(model);
            //获得历史数据
            List<Message> msgList = getChatMsg(dto.getUid(), dto.getCid());
            if (msgList.size()>=60){
                return Flux.just("超过对话回数上限");
            }
            //创建msg对象
            Message newMsg = new Message(dto.getCid(), "user", dto.getMsg());
            //插入新msg
            msgList.add(newMsg);
            redisUtils.pushListRight("chatId:"+dto.getCid()+"MsgList", newMsg);
            messageMapper.insert(newMsg);
            return Flux.<String>create(sink -> {
                try {
                    msgList.forEach((message -> {
                        message.setContent(message.getContent().replaceAll("[\\s\\n\\n ]+", " "));
                    }));
                    // 尝试获取一个许可，如果没有可用的许可，则当前线程会被阻塞

                    chatStrategy.getRequestConsent();
                    Response response = chatStrategy.ChatRequestHandle(msgList);
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {

                            String result = "";
                            String resultTemp;
                            StringBuilder resultBuilder = new StringBuilder();
                            BufferedSource source = responseBody.source();
                            while (!source.exhausted()) {
                                String line = source.readUtf8Line();
                                if (line.startsWith("data: ")) {
                                    line = line.substring(6);
                                    try {
//                                        if (result.length()>=2){
//                                            sink.next(result);
//                                            resultBuilder.append(result);
//                                            result="";
//                                        }
                                        resultTemp=chatStrategy.ChatResponseHandle(line);
                                        result = (resultTemp==null || resultTemp.equals("\n")) ? "":resultTemp;
                                        sink.next(result);
//                                        sink.next("data: "+ URLEncoder.encode(result, StandardCharsets.UTF_8)+"\n\n");
                                        resultBuilder.append(result);
                                    }catch (JSONException e){
//                                        e.printStackTrace();
                                        continue;
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                        sink.next("\n发生未知错误");
                                        break;
                                    }
                                }
                            }
                            result = resultBuilder.toString();
                            Message resultMsg = new Message(dto.getCid(), "assistant", result);
                            redisUtils.pushListRight("chatId:"+dto.getCid()+"MsgList", resultMsg);
                            messageMapper.insert(resultMsg);
                            sink.complete();
                            // 处理完成后释放一个许可
                            chatStrategy.releaseRequestConsent();
                        }
                    }
                    else {
                        // 获取状态码
                        int statusCode = response.code();
                        System.out.println("HTTP Status Code: " + statusCode);
                        // 获取响应体
                        String errorBody = response.body() != null ? response.body().string() : "No error body";
                        System.out.println("Error Body: " + errorBody);
                        sink.next("远端服务器错误");
                        sink.complete();
                        // 处理完成后释放一个许可
                        chatStrategy.releaseRequestConsent();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    sink.next(e.toString());
                    sink.complete();
                }finally {
                    // 处理完成后释放一个许可
                    chatStrategy.releaseRequestConsent();
                }
            }, FluxSink.OverflowStrategy.BUFFER)
                    .onBackpressureLatest()
                    .subscribeOn(Schedulers.boundedElastic())
                    .publishOn(Schedulers.parallel());
        }catch (Exception e){
            e.printStackTrace();
            return Flux.just("未找到对话或写入缓存错误"+e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Chat getchatByQid(Long questionId,Integer type, Long uid) throws StatusFailException {
        if (questionId==null || type==null || uid==null){
            throw new StatusFailException("参数不全或非法");
        }
        Chat chat=chatMapper.getQuestionAichat(questionId,uid);
        if (chat!=null){
            return chat;
        }

        String systemMsg;
        QuestionType Type=QuestionType.of(type);
        QuestionCoreStrategy strategy=questionStrategyFactory.getStrategy(Type);
        BaseQuestion question= strategy.getRawQuestion(questionId);
        if (question instanceof EduMultiQuestion){
            EduMultiQuestion multiQuestion = (EduMultiQuestion) question;
            systemMsg = systemMsgUtil.createSystemMsg(
                    multiQuestion.getQuestion(),
                    multiQuestion.getAnswerA(),
                    multiQuestion.getAnswerB(),
                    multiQuestion.getAnswerC(),
                    multiQuestion.getAnswerD()
            );
        }else {
            systemMsg=systemMsgUtil.createSystemMsg(question.getQuestion());
        }

        // 统一处理名称截取(增加长度校验)
        String chatName = question.getQuestion().length() > 10
                ? question.getQuestion().substring(0, 10)
                : question.getQuestion();

        // 创建chat对象
        chat=new Chat(YitIdHelper.nextId(),chatName,uid);

        //存储新创建的chat到mysql
        if (chatMapper.insert(chat)<0){
            throw new StatusFailException("创建失败");
        }
        // 关联表插入
        if (chatMapper.insertLinkChat(questionId,uid, chat.getCid())<0){
            throw new StatusFailException("创建失败");
        }
        // 系统消息插入
        Message msg=new Message(chat.getCid(), "system",systemMsg);
        if (messageMapper.insert(msg)<0){
            throw new StatusFailException("创建失败");
        }
        return chat;
    }



}
