package com.xh.online_edu.mapper.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.vo.StuAnswer;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StuAnswerMapper extends BaseMapper<StuAnswer> {

    @Select("SELECT * FROM stu_answer_vo " +
            "WHERE question_id = #{questionId} AND s_uid=#{sUid} " +
            "ORDER BY create_time " +
            "DESC LIMIT 5; ")
    ArrayList<StuAnswer> selectByQidAndSuid(Long questionId,Long sUid);

    @Select("SELECT * FROM stu_answer_vo " +
            "WHERE question_id = #{questionId} AND s_uid=#{sUid} " +
            "ORDER BY create_time " +
            "DESC LIMIT 5; ")
    @Results({
            @Result(property = "answerId",column = "answer_id"),
            @Result(property = "answer",column = "answer"),
            @Result(property = "score",column = "score"),
            @Result(property = "testTitle",column = "test_title"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "sUid",column = "s_uid"),
            @Result(property = "questionId",column = "question_id"),
            @Result(property = "aiMsg",column = "answer_id",one = @One(select = "selectAIMsgById"))
    })
    ArrayList<StuAnswer> selectByQidAndSuidAndGetAIMsg(Long questionId, Long sUid);

    @Select("SELECT ai_msg " +
            "FROM edu_aijudge_msg " +
            "WHERE answer_id=#{answerId}")
    String selectAIMsgById(Long answerId);
}
