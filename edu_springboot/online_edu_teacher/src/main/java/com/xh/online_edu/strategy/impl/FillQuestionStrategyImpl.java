package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.FillQuestionMapper;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.enums.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FillQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduFillQuestion,
        FillQuestionMapper,
        FillQuestionCoreStrategyImpl
        > {

    @Autowired
    public FillQuestionStrategyImpl(FillQuestionMapper mapper,FillQuestionCoreStrategyImpl coreStrategy){
        super(mapper,coreStrategy);
    }

    @Override
    protected void populateSpecificFields(EduFillQuestion question, NewQuestionDto dto) {

    }
}
