package com.xh.online_edu.strategy.impl.question;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.MultiQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.res.MultiQuestionRes;
import com.xh.online_edu.pojo.po.question.EduMultiQuestion;
import com.xh.online_edu.strategy.impl.MultiQuestionCoreStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduMultiQuestion,
        MultiQuestionRes,
        MultiQuestionMapper,
        MultiQuestionCoreStrategyImpl
        >{

    @Autowired
    private MultiQuestionMapper multiQuestionPoMapper;

    public MultiQuestionStrategyImpl(MultiQuestionMapper mapper, MultiQuestionCoreStrategyImpl coreStrategy) {
        super(mapper, coreStrategy);
    }

    protected MultiQuestionRes convertToRes(EduMultiQuestion po) {
        MultiQuestionRes res = new MultiQuestionRes();
        res.setQuestionId(po.getQuestionId());
        res.setQuestion(po.getQuestion());
        res.setAnswerA(po.getAnswerA());
        res.setAnswerB(po.getAnswerB());
        res.setAnswerC(po.getAnswerC());
        res.setAnswerD(po.getAnswerD());
        return res;
    }
}
