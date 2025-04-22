package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("edu_test")
public class EduTest {
    @TableId(value = "test_id",type = IdType.AUTO)
    private Long testId;
    private String testTitle;
    private String testDescription;
    private Long subjectId;
    public Long teacherId;
    public int isPublic;
}
