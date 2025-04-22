package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduTestPaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EduTestPaperMapper extends BaseMapper<EduTestPaper> {

    @Delete("<script>" +
            "DELETE FROM edu_test_paper WHERE " +
            "<foreach item='queId' collection='list' separator=' OR '>" +
            "(test_id = #{testId} AND question_id = #{queId})" +
            "</foreach>" +
            "</script>")
    int delQueObj(@Param("list") List<Long> questionIdList ,@Param("testId") Long testId);

//    @Delete("<script>" +
//            "DELETE FROM edu_test_paper WHERE " +
//            "<foreach item='condition' collection='list' separator=' OR '>" +
//            "(test_id = #{condition.test_id} AND question_id = #{condition.question_id})" +
//            "</foreach>" +
//            "</script>")
//    int deleteBatchMaps(@Param("list") List<>> conditions);

    @Insert("<script>" +
            "INSERT INTO edu_test_paper (test_id, question_idx, question_type, question_id, question_score) " +
            "VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{testId}, #{item.questionIdx}, #{item.questionType}, #{item.paperId.questionId}, #{item.questionScore})" +
            "</foreach>" +
            "ON DUPLICATE KEY UPDATE " +
            "question_idx = VALUES(question_idx), " +
            "question_type = VALUES(question_type), " +
            "question_score = VALUES(question_score)" +
            "</script>")
    int batchUpsert(@Param("list") List<EduTestPaper> list,@Param("testId") Long testId);
}
