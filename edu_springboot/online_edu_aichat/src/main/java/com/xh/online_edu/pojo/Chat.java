package com.xh.online_edu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: 小皓
 * @Date: 2024/8/23 17:36
 * @Description: Chat对象
 */

@Data
@TableName("aichat_chat")
@NoArgsConstructor
public class Chat {

    @TableId(value = "cid",type = IdType.INPUT)
    private Long cid;
    private String chatName;
    @JsonIgnore
    private Long uid;
    private LocalDateTime createTime;
//    private LinkedList<Message> msgList;

    public Chat(Long cid, Long uid){
        this.cid=cid;
        this.uid=uid;
        this.chatName="new Chat";
        this.createTime=LocalDateTime.now();
//        this.msgList=new LinkedList<>();
    }

    public Chat(Long cid, String chatName,Long uid){
        this.cid=cid;
        this.uid=uid;
        this.chatName=chatName;
        this.createTime=LocalDateTime.now();
//        this.msgList=new LinkedList<>();
    }

    public Chat(Long cid, String chatName,Long uid,LocalDateTime createTime){
        this.cid=cid;
        this.uid=uid;
        this.chatName=chatName;
        this.createTime=createTime;
//        this.msgList=new LinkedList<>();
    }
}
