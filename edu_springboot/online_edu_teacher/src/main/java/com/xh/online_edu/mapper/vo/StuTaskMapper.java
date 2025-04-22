package com.xh.online_edu.mapper.vo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.vo.StuTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StuTaskMapper extends BaseMapper<StuTask> {

    @Select("SELECT * " +
            "FROM stu_task_vo " +
            "WHERE course_id=#{courseId} " +
            "ORDER BY chapter_idx ASC,item_idx ASC")
    List<StuTask> selectTaskListByCourseId(Long courseId);

}
