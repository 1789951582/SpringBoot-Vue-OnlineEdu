package com.xh.online_edu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stu_answer_vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StuAnswer {
    private Long answerId;
    private String answer;
    private Integer score;
    private String testTitle;
    private LocalDateTime createTime;
    @JsonIgnore
    private Long sUid;
    @JsonIgnore
    private Long questionId;
    private String aiMsg;
}
