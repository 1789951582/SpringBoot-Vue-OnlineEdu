package com.xh.online_edu.strategy.impl.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.vo.StuAnswerMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.res.BaseQuestionRes;
import com.xh.online_edu.model.res.FillQuestionRes;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import com.xh.online_edu.pojo.vo.StuAnswer;
import com.xh.online_edu.strategy.QuestionStrategy;
import com.xh.online_edu.strategy.impl.BaseQuestionCoreStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public abstract class BaseQuestionStrategyImpl<
        T extends BaseQuestion,
        R extends BaseQuestionRes,
        M extends BaseMapper<T>,
        C extends BaseQuestionCoreStrategyImpl<T>
        > implements QuestionStrategy<T> {

    @Autowired
    StuAnswerMapper stuAnswerMapper;

    protected final M mapper;
    protected final C coreStrategy;

    public BaseQuestionStrategyImpl(M mapper, C coreStrategy) {
        this.mapper = mapper;
        this.coreStrategy = coreStrategy;
    }

    @Override
    public QuestionType getQuestionType() {
        return coreStrategy.getQuestionType();
    }

    @Override
    public T getRawQuestion(Long questionId) throws StatusFailException {
        return coreStrategy.getRawQuestion(questionId);
    }

    @Override
    public R getQuestion(Long questionId) throws StatusFailException {
        return convertToRes(getRawQuestion(questionId));
    }

    protected abstract R convertToRes(T po);

    @Override
    public ArrayList<StuAnswer> getStuAnswerList(Long questionId, Long uid){
        return stuAnswerMapper.selectByQidAndSuid(questionId,uid);
    }

}
