package com.xh.online_edu.mapper.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.vo.StuCourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StuCouresVoMapper extends BaseMapper<StuCouresVoMapper> {

    @Select("SELECT course_id,subject_title,subject_description,course_cover " +
            "From stu_course_vo " +
            "WHERE s_uid=#{uid} AND course_status=#{status} LIMIT #{offset}, #{size}")
    List<StuCourseVo> selectByPage(@Param("uid") Long uid, @Param("status") short status,@Param("offset") int offset,@Param("size") int size);

    @Select("SELECT COUNT(*) FROM stu_course_vo WHERE s_uid = #{uid} AND course_status=#{status}")
    int countByStudent(@Param("uid") Long uid ,@Param("status") short status);

    @Select("SELECT * " +
            "FROM stu_course_vo " +
            "WHERE s_uid=#{uid} AND course_id=#{courseId}")
    StuCourseVo getCourseInfo(Long uid,Long courseId);
}
