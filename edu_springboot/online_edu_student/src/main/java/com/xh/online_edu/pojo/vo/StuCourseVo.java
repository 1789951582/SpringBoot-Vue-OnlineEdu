package com.xh.online_edu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stu_course")
public class StuCourseVo {
    @JsonIgnore
    private Long sUid;
    private Long courseId;
    private String subjectTitle;
    private String subjectDescription;
    private String courseCover;
//    @JsonIgnore
    private short courseStatus;
    @JsonIgnore
    private LocalDateTime create;
}
