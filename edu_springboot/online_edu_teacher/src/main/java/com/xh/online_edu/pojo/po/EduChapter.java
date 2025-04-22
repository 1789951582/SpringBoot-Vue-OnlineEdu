package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("edu_chapter")
public class EduChapter {
    @TableId(value = "chapter_id",type = IdType.AUTO)
    private Long chapterId;
    private String chapterTitle;
    private Long subjectId;
    private Long teacherId;
    private int isPublic;
    private LocalDateTime createTime;
}
