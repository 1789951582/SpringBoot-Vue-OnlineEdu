package com.xh.online_edu.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.pojo.po.question.EduFillQuestion;
import com.xh.online_edu.strategy.QuestionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public abstract class BaseQuestionStrategyImpl<
        T extends BaseQuestion,
        M extends BaseMapper<T>,
        C extends BaseQuestionCoreStrategyImpl<T>
        > implements QuestionStrategy<T> {

    protected final M mapper;
    protected final C coreStrategy;

    public BaseQuestionStrategyImpl(M mapper, C coreStrategy) {
        this.mapper = mapper;
        this.coreStrategy = coreStrategy;
    }

    @Override
    public QuestionType getQuestionType(){
        return coreStrategy.getQuestionType();
    }

    @Override
    public T getRawQuestion(Long questionId) throws StatusFailException {
        return coreStrategy.getRawQuestion(questionId);
    }

    @Override
    public List<T> getQuestionList(Long subjectId) {
        QueryWrapper<T> fillQuestionQueryWrapper=new QueryWrapper<>();
        fillQuestionQueryWrapper.eq("subject_id",subjectId);
        return mapper.selectList(fillQuestionQueryWrapper);
    }

    @Override
    public T insertNewQuestion(NewQuestionDto dto) throws StatusFailException {
        T question= this.buildQuestion(dto,false);
        mapper.insert(question);
        return question;
    }

    @Override
    public int updateQuestion(NewQuestionDto dto) throws StatusFailException {
        T question= this.buildQuestion(dto,true);
        return mapper.updateById(question);
    }

    @Override
    public int delQuestion(Long questionId) {
        return mapper.deleteById(questionId);
    }

    protected T buildQuestion(NewQuestionDto dto ,boolean isUpdate) throws StatusFailException {
        try{
            T question = (T) getQuestionType().getPoClass().getDeclaredConstructor().newInstance();
            populateCommonFields(question, dto,isUpdate);
            populateSpecificFields(question, dto);
            return question;
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new StatusFailException("异常错误,请手动重试！");
        }
    }

    private void populateCommonFields(BaseQuestion question, NewQuestionDto dto,boolean isUpdate){
        question.setQuestion(dto.getQuestion());
        question.setRightAnswer(dto.getRightAnswer());
        question.setAnalysis(dto.getAnalysis());
        question.setLevel(dto.getLevel());
        question.setSubjectId(dto.getSubjectId());
        if (!isUpdate) {
            question.setCreateTeacherId(dto.getUid());
        } else {
            question.setUpdateTeacherId(dto.getUid());
            question.setQuestionId(dto.getQuestionId());
        }
    }

    protected abstract void populateSpecificFields(T question, NewQuestionDto dto);

}
