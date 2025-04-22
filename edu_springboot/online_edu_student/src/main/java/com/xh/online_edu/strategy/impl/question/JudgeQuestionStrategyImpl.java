package com.xh.online_edu.strategy.impl.question;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.JudgeQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.res.JudgeQuestionRes;
import com.xh.online_edu.pojo.po.question.EduJudgeQuestion;
import com.xh.online_edu.strategy.impl.JudgeQuestionCoreStrategyImpl;
import com.xh.online_edu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JudgeQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduJudgeQuestion,
        JudgeQuestionRes,
        JudgeQuestionMapper,
        JudgeQuestionCoreStrategyImpl
        > {


    public JudgeQuestionStrategyImpl(JudgeQuestionMapper mapper, JudgeQuestionCoreStrategyImpl coreStrategy) {
        super(mapper, coreStrategy);
    }

    protected JudgeQuestionRes convertToRes(EduJudgeQuestion po) {
        JudgeQuestionRes res = new JudgeQuestionRes();
        res.setQuestionId(po.getQuestionId());
        res.setQuestion(po.getQuestion());
        return res;
    }
}
