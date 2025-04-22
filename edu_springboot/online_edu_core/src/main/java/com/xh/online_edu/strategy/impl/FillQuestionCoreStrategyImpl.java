package com.xh.online_edu.strategy.impl;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.FillQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FillQuestionCoreStrategyImpl extends BaseQuestionCoreStrategyImpl<EduFillQuestion> {
    @Autowired
    FillQuestionMapper fillQuestionMapper;

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.FILL;
    }

    @Override
    public EduFillQuestion getRawQuestion(Long questionId) throws StatusFailException {
        EduFillQuestion po;
        String cacheKey=buildCacheKey(questionId);
        EduFillQuestion cached=redisUtils.get(cacheKey,EduFillQuestion.class);
        if (cached != null) {
            redisUtils.expire(cacheKey, 3600 * 24);
            po= cached;
        }else{
            po=fillQuestionMapper.selectById(questionId);
            if (po==null){
                throw new  StatusFailException("找不到题目");
            }
            redisUtils.set(cacheKey,po,3600*24);
        }
        return po;
    }
}
