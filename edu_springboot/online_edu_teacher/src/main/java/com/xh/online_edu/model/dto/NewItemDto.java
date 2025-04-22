package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class NewItemDto extends BaseDto{
    private String itemTitle;
    private Long chapterId;
    private Integer isPublic;
}
