package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("edu_chapter_item")
public class EduChapterItem {
    @TableId(value = "item_id",type = IdType.AUTO)
    private Long itemId;

    private String itemTitle;
    private Long chapterId;
    private Long teacherId;
    private int isPublic;

    @TableField(fill = FieldFill.INSERT)
    public LocalDateTime createTime;

    public EduChapterItem(String itemTitle, Long chapterId, Long teacherId, int isPublic){
        this.setItemTitle(itemTitle);
        this.setChapterId(chapterId);
        this.setTeacherId(teacherId);
        this.setIsPublic(isPublic);
    }
    public EduChapterItem(){}
}
