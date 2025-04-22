package com.xh.online_edu.pojo.vo;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stu_test_vo")
public class StuTestVo {
    @JsonIgnore
    private Long courseId;
    private Long testId;
    private String testTitle;
    private String testDescription;
    private LocalDateTime endTime;
    private Short status;
}
