package com.xh.online_edu.strategy.impl.question;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.SubjectiveQuestionMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.model.res.SubjectiveQuestionRes;
import com.xh.online_edu.pojo.po.question.EduSubjectiveQuestion;
import com.xh.online_edu.pojo.vo.StuAnswer;
import com.xh.online_edu.strategy.impl.SubjectiveQuestionCoreStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectiveQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduSubjectiveQuestion,
        SubjectiveQuestionRes,
        SubjectiveQuestionMapper,
        SubjectiveQuestionCoreStrategyImpl
        >{

    public SubjectiveQuestionStrategyImpl(SubjectiveQuestionMapper mapper, SubjectiveQuestionCoreStrategyImpl coreStrategy) {
        super(mapper, coreStrategy);
    }

    protected SubjectiveQuestionRes convertToRes(EduSubjectiveQuestion po) {
        SubjectiveQuestionRes res = new SubjectiveQuestionRes();
        res.setQuestionId(po.getQuestionId());
        res.setQuestion(po.getQuestion());
        return res;
    }

    @Override
    public ArrayList<StuAnswer> getStuAnswerList(Long questionId, Long uid) {
        return stuAnswerMapper.selectByQidAndSuidAndGetAIMsg(questionId,uid);
    }
}
