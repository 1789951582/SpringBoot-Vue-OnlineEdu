package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.model.res.CommitListRes;
import com.xh.online_edu.pojo.po.EduTestCommit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EduTestCommitPoMapper extends BaseMapper<EduTestCommit> {

    @Select("SELECT test_id,test_title,test_description " +
            "FROM edu_test " +
            "WHERE test_id=#{testId}")
    @Results({
            @Result(property = "test_id",column = "testId"),
            @Result(property = "test_title",column = "testTitle"),
            @Result(property = "test_description",column = "testDescription"),
    })
    CommitListRes selectCommit(Long testId);

    @Select("SELECT commit_id,score,create_time " +
            "FROM edu_test_commit " +
            "WHERE s_uid=#{uid} AND test_id=#{testId}")
    List<EduTestCommit> selectListByUidAndTid(Long uid,Long testId);
}
