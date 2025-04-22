package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    @Update("UPDATE edu_chapter " +
            "SET chapter_title=#{chapterTitle},is_public=#{isPublic} " +
            "WHERE chapter_id=#{chapterId}")
    int myUpdate(Long chapterId, String chapterTitle, Integer isPublic);
}
