package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class ResetItemDto extends BaseDto{
    private Long itemId;
    private Long newChapterId;
    private Integer isPublic;
}
