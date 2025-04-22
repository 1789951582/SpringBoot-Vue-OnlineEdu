package com.xh.online_edu.pojo.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EduReleaseTest {
    private Long courseId;
    private Long testId;
    private LocalDateTime endTime;
    private int status=1;
}
