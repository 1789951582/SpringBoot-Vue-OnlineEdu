package com.xh.online_edu.strategy;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.res.BaseQuestionRes;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.pojo.vo.StuAnswer;

import java.util.ArrayList;
import java.util.List;

public interface QuestionStrategy<T extends BaseQuestion> extends QuestionCoreStrategy<T>{

    //根据ID获取题目
    BaseQuestionRes getQuestion(Long questionId) throws StatusFailException;

    //获取学生答案
    ArrayList<StuAnswer> getStuAnswerList(Long questionId, Long uid);

}
