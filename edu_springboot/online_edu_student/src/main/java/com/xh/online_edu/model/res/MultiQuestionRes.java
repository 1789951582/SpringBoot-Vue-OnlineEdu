package com.xh.online_edu.model.res;

import lombok.Data;

@Data
public class MultiQuestionRes extends BaseQuestionRes{
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
}
