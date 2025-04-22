package com.xh.online_edu.mapper.po.question;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.model.res.QuestionData;
import com.xh.online_edu.pojo.po.question.EduJudgeQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JudgeQuestionMapper extends BaseMapper<EduJudgeQuestion> {
    @Select("SELECT question_id,question,level,teacher_id " +
            "FROM edu_judge_question " +
            "WHERE subject_id=#{subjectId}")
    List<QuestionData> getQuestionList(Long subjectId);
}
