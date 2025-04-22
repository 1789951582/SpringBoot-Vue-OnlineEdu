package com.xh.online_edu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class NewQuestionDto extends BaseDto{
    private Long questionId;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String rightAnswer;
    private String analysis;
    private int level;
    private Long subjectId;
//    private Long teacherId;
}
