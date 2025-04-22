package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xh.online_edu.model.enums.QuestionType;
import lombok.Data;

@Data
@TableName("edu_test_paper")
public class EduTestPaperPo {
    @JsonIgnore
    private Long testId;
    private Integer questionIdx;
    private Integer questionType;
    private Long questionId;
    private Integer questionScore;
}
