package com.xh.online_edu.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xh.online_edu.pojo.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ChatRes {
    private Long cid;
    @JsonIgnore
    private Long uid;
    private String chatName;
    private LocalDateTime createTime;
    private List<Message> msgList;
}
