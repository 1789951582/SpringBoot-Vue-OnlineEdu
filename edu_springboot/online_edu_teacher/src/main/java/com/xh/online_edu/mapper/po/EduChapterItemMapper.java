package com.xh.online_edu.mapper.po;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xh.online_edu.pojo.po.EduChapterItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EduChapterItemMapper extends BaseMapper<EduChapterItem> {
    @Update("UPDATE edu_chapter_item SET chapter_id=#{chapterId} WHERE item_id=#{itemId}")
    int resetItemChapter(Long itemId,Long chapterId);

    @Update("UPDATE edu_chapter_item SET is_public=#{isPublic} WHERE item_id=#{itemId}")
    int resetItemPublic(Long itemId, Integer isPublic);
}
