package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.mapper.po.question.JudgeQuestionMapper;
import com.xh.online_edu.pojo.po.question.EduJudgeQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.enums.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JudgeQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduJudgeQuestion,
        JudgeQuestionMapper,
        JudgeQuestionCoreStrategyImpl
        > {

    @Autowired
    public JudgeQuestionStrategyImpl(JudgeQuestionMapper mapper,JudgeQuestionCoreStrategyImpl coreStrategy){
        super(mapper,coreStrategy);
    }

    @Override
    protected void populateSpecificFields(EduJudgeQuestion question, NewQuestionDto dto) {

    }
}
