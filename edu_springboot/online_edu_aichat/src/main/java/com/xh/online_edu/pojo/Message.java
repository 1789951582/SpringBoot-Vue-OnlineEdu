package com.xh.online_edu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("aichat_msg")
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    @TableId(value = "mid",type = IdType.AUTO)
    private Long mid;
    @JsonIgnore
    private Long cid;
    private String role;
    private String content;
    private LocalDateTime createTime;

    public Message(long cid, String role, String content){
        this.cid=cid;
        this.role=role;
        this.content=content;
        this.createTime=LocalDateTime.now();
    }
}
