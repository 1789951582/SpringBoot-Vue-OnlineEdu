package com.xh.online_edu.pojo.vo;

import lombok.Data;

@Data
public class StuTask {
    private Long courseId;
    private Long chapterId;
    private Integer chapterIdx;
    private String chapterTitle;
    private Long itemId;
    private Integer itemIdx;
    private String itemTitle;
}
