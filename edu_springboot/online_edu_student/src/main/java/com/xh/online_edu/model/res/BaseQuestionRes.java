package com.xh.online_edu.model.res;

import lombok.Data;

@Data
public abstract class BaseQuestionRes {
    private Long questionId;
    private String question;
}
