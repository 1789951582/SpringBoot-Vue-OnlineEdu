package com.xh.online_edu.mapper.vo;

import com.xh.online_edu.pojo.vo.StuTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StuTaskVoMapper {
    @Select("SELECT * " +
            "FROM stu_task_vo " +
            "WHERE course_id=#{courseId} " +
            "ORDER BY chapter_idx ASC,item_idx ASC")
    List<StuTaskVo> selectTaskListByCourseId(Long courseId);
}
