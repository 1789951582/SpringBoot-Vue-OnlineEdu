package com.xh.online_edu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tch_course_vo")
public class TchCourse {
    private Long courseId;
    private String classTitle;
    private Long subjectId;
    private int courseStatus;
}
