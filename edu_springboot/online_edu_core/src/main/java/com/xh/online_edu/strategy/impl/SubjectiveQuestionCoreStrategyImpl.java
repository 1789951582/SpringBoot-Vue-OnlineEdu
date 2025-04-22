package com.xh.online_edu.strategy.impl;

import com.xh.online_edu.mapper.po.question.SubjectiveQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.EduSubjectiveQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectiveQuestionCoreStrategyImpl extends BaseQuestionCoreStrategyImpl<EduSubjectiveQuestion> {

    @Autowired
    SubjectiveQuestionMapper subjectiveQuestionMapper;

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.SUBJECTIVE;
    }

    @Override
    public EduSubjectiveQuestion getRawQuestion(Long questionId) {
        EduSubjectiveQuestion po;
        String cacheKey=buildCacheKey(questionId);
        EduSubjectiveQuestion cached=redisUtils.get(cacheKey,EduSubjectiveQuestion.class);
        if (cached != null) {
            redisUtils.expire(cacheKey, 3600 * 24);
            po= cached;
        }else{
            po=subjectiveQuestionMapper.selectById(questionId);
            redisUtils.set(cacheKey,po,3600*24);
        }
        return po;
    }
}
