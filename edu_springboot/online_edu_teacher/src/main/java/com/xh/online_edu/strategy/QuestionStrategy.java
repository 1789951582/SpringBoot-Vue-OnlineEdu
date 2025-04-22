package com.xh.online_edu.strategy;

import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.enums.QuestionType;

import java.util.List;

public interface QuestionStrategy<T extends BaseQuestion> extends QuestionCoreStrategy<T>{

    //获取题目列表
    List<T> getQuestionList(Long subjectId);

    //新增
    T insertNewQuestion(NewQuestionDto dto) throws StatusFailException;

    int updateQuestion(NewQuestionDto dto) throws StatusFailException;

    int delQuestion(Long questionId);

}
