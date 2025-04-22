package com.xh.online_edu.strategy.impl;

import com.xh.online_edu.mapper.po.question.MultiQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.EduMultiQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiQuestionCoreStrategyImpl extends BaseQuestionCoreStrategyImpl<EduMultiQuestion> {

    @Autowired
    MultiQuestionMapper multiQuestionMapper;

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.MULTI;
    }

    @Override
    public EduMultiQuestion getRawQuestion(Long questionId) {
        EduMultiQuestion po;
        String cacheKey=buildCacheKey(questionId);
        EduMultiQuestion cached=redisUtils.get(cacheKey,EduMultiQuestion.class);
        if (cached!=null){
            redisUtils.expire(cacheKey, 3600 * 24);
            po= cached;
        }else{
            po=multiQuestionMapper.selectById(questionId);
            redisUtils.set(cacheKey,po,3600*24);
        }
        return po;
    }
}
