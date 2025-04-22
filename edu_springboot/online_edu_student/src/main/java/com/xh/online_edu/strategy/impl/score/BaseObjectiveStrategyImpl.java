package com.xh.online_edu.strategy.impl.score;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import com.xh.online_edu.strategy.QuestionStrategy;
import com.xh.online_edu.strategy.ScoreCalculateStrategy;
import com.xh.online_edu.strategy.factory.QuestionStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseObjectiveStrategyImpl implements ScoreCalculateStrategy {

    @Autowired
    private QuestionStrategyFactory questionStrategyFactory;

    protected double baseCalculate(EduTestPaperPo paperQuestion, String studentAnswer) {
        try{
            QuestionStrategy strategy = questionStrategyFactory.getStrategy(getQuestionType());
            // 获取正确答案
            BaseQuestion question = strategy.getRawQuestion(paperQuestion.getQuestionId());
            return question.getRightAnswer().equals(studentAnswer) ? paperQuestion.getQuestionScore() : 0;
        }catch (StatusFailException e){
            log.error("处理"+paperQuestion.getQuestionId()+"时出现"+e.getMessage());
            return 0;
        }
    }
}
