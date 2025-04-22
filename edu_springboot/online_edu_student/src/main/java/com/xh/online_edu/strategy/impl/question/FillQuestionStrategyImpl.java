package com.xh.online_edu.strategy.impl.question;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.FillQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.res.BaseQuestionRes;
import com.xh.online_edu.model.res.FillQuestionRes;
import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import com.xh.online_edu.strategy.impl.FillQuestionCoreStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FillQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduFillQuestion,
        FillQuestionRes,
        FillQuestionMapper,
        FillQuestionCoreStrategyImpl
        > {

    @Autowired
    public FillQuestionStrategyImpl(FillQuestionMapper mapper,FillQuestionCoreStrategyImpl coreStrategy){
        super(mapper,coreStrategy);
    }

    protected FillQuestionRes convertToRes(EduFillQuestion po) {
        FillQuestionRes res = new FillQuestionRes();
        res.setQuestionId(po.getQuestionId());
        res.setQuestion(po.getQuestion());
        return res;
    }
}
