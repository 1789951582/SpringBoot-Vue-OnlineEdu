package com.xh.online_edu.strategy;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.BaseQuestion;

import java.util.List;

public interface QuestionCoreStrategy<T extends BaseQuestion> {
    // 统一标识方法
    QuestionType getQuestionType();

    // 核心方法：获取原始PO对象
    T getRawQuestion(Long questionId) throws StatusFailException;
}
