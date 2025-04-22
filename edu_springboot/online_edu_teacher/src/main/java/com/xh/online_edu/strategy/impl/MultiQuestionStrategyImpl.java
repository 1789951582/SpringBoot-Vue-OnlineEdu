package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.mapper.po.question.MultiQuestionMapper;
import com.xh.online_edu.pojo.po.question.EduMultiQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.enums.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MultiQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduMultiQuestion,
        MultiQuestionMapper,
        MultiQuestionCoreStrategyImpl
        > {

    @Autowired
    public MultiQuestionStrategyImpl(MultiQuestionMapper mapper, MultiQuestionCoreStrategyImpl coreStrategy) {
        super(mapper, coreStrategy);
    }

    @Override
    protected void populateSpecificFields(EduMultiQuestion question, NewQuestionDto dto) {
        question.setAnswerA(dto.getAnswerA());
        question.setAnswerB(dto.getAnswerB());
        question.setAnswerC(dto.getAnswerC());
        question.setAnswerD(dto.getAnswerD());
    }
}
