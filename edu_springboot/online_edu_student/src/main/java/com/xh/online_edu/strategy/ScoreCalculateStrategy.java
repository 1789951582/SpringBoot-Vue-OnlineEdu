package com.xh.online_edu.strategy;

import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduTestPaperPo;

import java.util.List;

public interface ScoreCalculateStrategy {
    // 返回该题得分
    double calculateScore(EduTestPaperPo paperQuestion, String studentAnswer,Long answerId);

    // 支持的题目类型
    QuestionType getQuestionType();
}
