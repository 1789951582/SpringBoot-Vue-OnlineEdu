package com.xh.online_edu.strategy.impl;

import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.strategy.QuestionCoreStrategy;
import com.xh.online_edu.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public  abstract class BaseQuestionCoreStrategyImpl<T extends BaseQuestion> implements QuestionCoreStrategy<T> {

    @Autowired
    RedisUtils redisUtils;

    protected String buildCacheKey(Long id) {
        System.out.println(getQuestionType().getDesc());
        return String.format("question:type:%s:id:%d", getQuestionType().getDesc(), id);
    }

}
