package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EduTestMapper extends BaseMapper<EduTest> {
    @Update("UPDATE edu_test " +
            "SET is_public=#{val} " +
            "WHERE test_id=#{testId}")
    int setPublic(Long testId, Integer val);

    @Update("UPDATE edu_test " +
            "SET test_title=#{testTitle},test_description=#{testDescription}" +
            "WHERE test_id=#{testId}")
    int rewriteTest(Long testId, String testTitle,String testDescription);
}
