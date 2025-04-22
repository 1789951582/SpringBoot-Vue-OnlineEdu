package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.JudgeQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.EduJudgeQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JudgeQuestionCoreStrategyImpl extends BaseQuestionCoreStrategyImpl<EduJudgeQuestion> {
    @Autowired
    JudgeQuestionMapper judgeQuestionMapper;

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.JUDGE;
    }

    @Override
    public EduJudgeQuestion getRawQuestion(Long questionId) {
        EduJudgeQuestion po;
        String cacheKey=buildCacheKey(questionId);
        EduJudgeQuestion cached=redisUtils.get(cacheKey,EduJudgeQuestion.class);
        if (cached != null) {
            redisUtils.expire(cacheKey, 3600 * 24);
            po=cached;
        }else{
            po=judgeQuestionMapper.selectById(questionId);
            redisUtils.set(cacheKey,po,3600*24);
        }
        return po;
    }
}
