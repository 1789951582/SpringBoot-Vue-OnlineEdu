package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class NewTestDto extends BaseDto{
    private Long testId;
    private String testTitle;
    private String testDescription;
    private int isPublic;
}
