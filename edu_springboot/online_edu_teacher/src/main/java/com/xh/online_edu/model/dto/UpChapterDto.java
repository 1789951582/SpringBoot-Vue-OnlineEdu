package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class UpChapterDto extends BaseDto{
    private String chapterTitle;
    private Integer isPublic;
}
