package com.xh.online_edu.strategy.impl.score;

import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import org.springframework.stereotype.Component;

@Component
public class FillScoreStrategyImpl extends BaseObjectiveStrategyImpl{
    @Override
    public double calculateScore(EduTestPaperPo paperQuestion, String studentAnswer,Long answer) {
        return baseCalculate(paperQuestion, studentAnswer);
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.FILL;
    }
}
