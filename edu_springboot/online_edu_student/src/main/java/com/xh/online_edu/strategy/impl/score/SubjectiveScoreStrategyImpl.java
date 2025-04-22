package com.xh.online_edu.strategy.impl.score;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.online_edu.mapper.po.EduAijudgeMsgPoMapper;
import com.xh.online_edu.mapper.po.question.SubjectiveQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduAijudgeMsgPo;
import com.xh.online_edu.pojo.po.question.EduSubjectiveQuestion;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import com.xh.online_edu.strategy.ScoreCalculateStrategy;
import com.xh.online_edu.utils.BailianAiJudgeUtils;
import com.xh.online_edu.utils.BailianAiResParserUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SubjectiveScoreStrategyImpl implements ScoreCalculateStrategy {
    @Autowired
    BailianAiJudgeUtils aiJudgeUtils;
    @Autowired
    SubjectiveQuestionMapper subjectiveQuestionPoMapper;
    @Autowired
    EduAijudgeMsgPoMapper aijudgeMsgPoMapper;

    static final private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public double calculateScore(EduTestPaperPo paperQuestion, String studentAnswer,Long answerId) {
        EduSubjectiveQuestion subjectiveQuestionPo=subjectiveQuestionPoMapper.selectById(paperQuestion.getQuestionId());
        String responseBody = null;
        try {
            Response response = aiJudgeUtils.reqJudge(subjectiveQuestionPo.getQuestion(), studentAnswer);
            if (response.isSuccessful() && response.body()!=null) {
                responseBody  = response.body().string();
                BailianAiResParserUtils.ParseResult result = BailianAiResParserUtils.parseScore(responseBody, paperQuestion.getQuestionScore());
                EduAijudgeMsgPo aijudgeMsgPo=new EduAijudgeMsgPo();
                aijudgeMsgPo.setAnswerId(answerId);
                aijudgeMsgPo.setAiMsg(result.getFeedback());
                aijudgeMsgPoMapper.insert(aijudgeMsgPo);
                return result.getActualScore();
            }else {
                log.error(String.format("questionType:%d,questionId:%d,网络请求出错",paperQuestion.getQuestionType(),paperQuestion.getQuestionId()));
                return 0;
            }
        } catch (JsonProcessingException e){
            if (responseBody ==null){
                log.error(String.format("questionType:%d,questionId:%d,构建请求体出错,msg:%s",paperQuestion.getQuestionType(),paperQuestion.getQuestionId(),e.getMessage()));
            }else {
                log.error(String.format("questionType:%d,questionId:%d,解析ai内容出错,msg:%s,ai返回内容为:%s",paperQuestion.getQuestionType(),paperQuestion.getQuestionId(),e.getMessage(),responseBody));
            }
            return 0;
        } catch (IOException e){
            log.error(String.format("questionType:%d,questionId:%d,请求ai判题出错,msg:%s",paperQuestion.getQuestionType(),paperQuestion.getQuestionId(),e.getMessage()));
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.SUBJECTIVE;
    }
}
