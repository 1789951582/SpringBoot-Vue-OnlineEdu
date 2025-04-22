package com.xh.online_edu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tch_subject_vo")
public class TchSubject {
    private Long tUid;
    private Long subjectId;
    private String subjectTitle;
    private String subjectDescription;
    private Long directorId;
    private String cover;
}
