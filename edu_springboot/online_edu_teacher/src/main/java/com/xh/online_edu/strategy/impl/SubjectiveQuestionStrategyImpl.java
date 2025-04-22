package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.pojo.po.question.EduSubjectiveQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.question.SubjectiveQuestionMapper;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.enums.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectiveQuestionStrategyImpl extends BaseQuestionStrategyImpl<
        EduSubjectiveQuestion,
        SubjectiveQuestionMapper,
        SubjectiveQuestionCoreStrategyImpl
        > {

    @Autowired
    public SubjectiveQuestionStrategyImpl(SubjectiveQuestionMapper mapper, SubjectiveQuestionCoreStrategyImpl coreStrategy) {
        super(mapper, coreStrategy);
    }


//    @Autowired
//    SubjectiveQuestionMapper subjectiveQuestionMapper;
//    @Autowired
//    SubjectiveQuestionCoreStrategyImpl coreStrategy;
//
//    @Override
//    public QuestionType getQuestionType() {
//        return coreStrategy.getQuestionType();
//    }
//
//    @Override
//    public EduSubjectiveQuestion getRawQuestion(Long questionId) {
//        return coreStrategy.getRawQuestion(questionId);
//    }
//
//    @Override
//    public List<EduSubjectiveQuestion> getQuestionList(Long subjectId) {
//        QueryWrapper<EduSubjectiveQuestion> fillQuestionQueryWrapper=new QueryWrapper<>();
//        fillQuestionQueryWrapper.eq("subject_id",subjectId);
//        return subjectiveQuestionMapper.selectList(fillQuestionQueryWrapper);
//    }
//
//    @Override
//    public EduSubjectiveQuestion insertNewQuestion(NewQuestionDto dto) throws StatusFailException {
//        EduSubjectiveQuestion question=super.buildQuestion(dto,false);
//        subjectiveQuestionMapper.insert(question);
//        return question;
//    }
//
//    @Override
//    public int updateQuestion(NewQuestionDto dto) throws StatusFailException {
//        EduSubjectiveQuestion question=super.buildQuestion(dto,true);
//        return subjectiveQuestionMapper.updateById(question);
//
//    }
//
//    @Override
//    public int delQuestion(Long questionId) {
//        return subjectiveQuestionMapper.deleteById(questionId);
//    }

    @Override
    protected void populateSpecificFields(EduSubjectiveQuestion question, NewQuestionDto dto) {

    }
}
