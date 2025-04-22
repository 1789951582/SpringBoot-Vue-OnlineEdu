package com.xh.online_edu.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReleaseTestDto extends BaseDto{
    private Long courseId;
    private Long testId;
    private LocalDateTime endTime;
}
