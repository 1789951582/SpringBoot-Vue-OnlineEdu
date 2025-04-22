package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stu_test_answer")
public class StuTestAnswer {
    @TableId(type = IdType.INPUT)
    private Long answerId;
    private String answer;
    private double score;
//    private Long sUid;
//    private Long testId;
    private Long commitId;
    private Long questionId;
//    private LocalDateTime create;
}
