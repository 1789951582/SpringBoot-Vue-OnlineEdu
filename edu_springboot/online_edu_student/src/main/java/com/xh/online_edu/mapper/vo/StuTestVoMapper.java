package com.xh.online_edu.mapper.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xh.online_edu.model.res.StuTestRes;
import com.xh.online_edu.pojo.vo.StuTestVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StuTestVoMapper extends BaseMapper<StuTestVo> {
    @Select("SELECT t.*, " +
            "(CASE WHEN EXISTS (SELECT 1 FROM edu_test_commit c WHERE c.test_id = t.test_id AND c.s_uid=#{uid}) THEN 1 ELSE 0 END) as has_commit " +
            "FROM stu_test_vo t " +
            "WHERE course_id = #{courseId}")
    IPage<StuTestRes> selectTestWithCommitStatus(Page<StuTestRes> page, @Param("uid") Long uid, @Param("courseId") Long courseId);
}
