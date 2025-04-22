package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class NewChapterDto extends BaseDto{
    private String chapterTitle;
    private int isPublic;
}
