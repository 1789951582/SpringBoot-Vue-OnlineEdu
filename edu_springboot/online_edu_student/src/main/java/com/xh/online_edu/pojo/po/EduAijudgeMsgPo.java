package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("edu_aijudge_msg")
public class EduAijudgeMsgPo {
    private Long answerId;
    private String aiMsg;
    private LocalDateTime createTime;
}
