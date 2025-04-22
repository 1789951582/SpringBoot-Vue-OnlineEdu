package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduTask;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EduTaskMapper extends BaseMapper<EduTask> {
    @Select("SELECT chapter_idx,MAX(item_idx) AS max_item_idx " +
            "FROM edu_task " +
            "WHERE course_id=#{courseId} AND chapter_id=#{chapterId} " +
            "GROUP BY chapter_idx")
    Map<String,Integer> selectItemIdx(Long courseId,Long chapterId);

    @Select("SELECT MAX(chapter_idx) AS max_chapter_idx " +
            "FROM edu_task " +
            "WHERE course_id=#{courseId}")
    Integer selectChapterIdx(Long courseId);

    @Insert("<script>" +
            "INSERT INTO edu_task (course_id, item_id, chapter_id, chapter_idx, item_idx) " +
            "VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.courseId}, #{item.itemId}, #{item.chapterId}, #{item.chapterIdx}, #{item.itemIdx})" +
            "</foreach>" +
            "ON DUPLICATE KEY UPDATE " +
            "item_idx = VALUES(item_idx)" +
            "</script>")
    int batchUpsert(@Param("list") List<EduTask> tasks);
}


