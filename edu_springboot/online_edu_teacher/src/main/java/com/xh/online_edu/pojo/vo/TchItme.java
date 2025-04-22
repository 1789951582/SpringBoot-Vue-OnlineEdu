package com.xh.online_edu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tch_item_vo")
public class TchItme {
    @JsonIgnore
    private Long subjectId;
//    private Long directorId;
    private Long chapterId;
//    private String chapterTitle;
    private Long itemId;
    private String itemTitle;
    private Long teacherId;
    private Integer isPublic;
    private LocalDateTime createTime;
}
