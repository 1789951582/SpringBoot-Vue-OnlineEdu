package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseDto;
import com.xh.online_edu.model.dto.ChatDto;
import com.xh.online_edu.model.dto.RenameDto;
import com.xh.online_edu.pojo.Chat;
import com.xh.online_edu.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/aichat")
public class ChatController {

    @Autowired
    ChatService chatService;

    //创建一般chat
    @PostMapping("new_chat")
    public Result newChat(@RequestBody BaseDto baseDto) {
        try{
            return Result.successResponse(chatService.newChat(baseDto.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    //修改chat名
    @PostMapping("rename")
    public Result rename(@RequestBody RenameDto dto){
        try {
            return Result.successResponse(chatService.rename(dto.getCid(), dto.getUid(), dto.getNewName()),"修改成功");
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    //获取历史对话记录
    @PostMapping("get_chat_list")
    public Result getHistoryChatList(@RequestBody BaseDto dto){
        try{
            return Result.successResponse(chatService.getHistoryChatListByUid(dto.getUid()));
        }catch (Exception e){
            e.printStackTrace();
            return Result.FailResponse("查询失败");
        }
    }

    //删除对话
    @PostMapping("delchat/{cid}")
    public Result delChat(@RequestBody BaseDto dto ,@PathVariable Long cid){
        try{
            return Result.successResponse(chatService.delChat(cid,dto.getUid()),"删除成功");
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("get_chat_msg/{cid}")
    public Result getChatMsg(@RequestBody BaseDto dto ,@PathVariable Long cid){
        try{
            return Result.successResponse(chatService.getChatMsg(dto.getUid(),cid));
        }catch (Exception e){
            return Result.FailResponse("获取失败");
        }
    }

    @PostMapping("chat")
    public Flux<String> chat(@RequestBody ChatDto dto){
        return chatService.chat(dto)
                .delayElements(Duration.ofMillis(50));
//                .map(data->"data: "+data+"\n\n");
    }

    //创建题目chat
    @PostMapping("get_question_chat/{type}/{questionId}")
    public Result getchatByQid(@PathVariable Long questionId,@PathVariable Integer type,@RequestBody BaseDto baseDto) {
        try{
            return Result.successResponse(chatService.getchatByQid(questionId,type,baseDto.getUid()));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }
}
