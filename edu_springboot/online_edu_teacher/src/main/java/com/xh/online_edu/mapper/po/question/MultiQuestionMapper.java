//package com.xh.online_edu_teacher.mapper.po.question;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.xh.online_edu_teacher.model.resData.QuestionData;
//import com.xh.online_edu_teacher.pojo.po.question.BaseQuestion;
//import com.xh.online_edu_teacher.pojo.po.question.EduMultiQuestion;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//
//import java.util.List;
//
//@Mapper
//public interface MultiQuestionMapper extends BaseMapper<EduMultiQuestion> {
//    @Select("SELECT question_id,question,level,teacher_id " +
//            "FROM edu_multi_question " +
//            "WHERE subject_id=#{subjectId}")
//    List<QuestionData> getQuestionList(Long subjectId);
//}
