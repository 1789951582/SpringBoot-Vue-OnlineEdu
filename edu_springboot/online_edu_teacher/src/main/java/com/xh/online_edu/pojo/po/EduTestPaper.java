package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("edu_test_paper")
public class EduTestPaper implements Serializable{

    @TableId(type = IdType.INPUT)
    private PaperId paperId=new PaperId();

    private Integer questionIdx;
    private Integer questionType;
    private Integer questionScore;

    public Long getTestId(){
        return this.paperId.getTestId();
    }
    public void setTestId(Long testId){
        this.paperId.setTestId(testId);
    }

    public Long getQuestionId(){
        return this.paperId.getQuestionId();
    }
    public void setQuestionId(Long questionId){
        this.paperId.setQuestionId(questionId);
    }

    @Data
    @NoArgsConstructor
    private static class PaperId implements Serializable{

        @JsonIgnore
        @TableField("test_id")
        private Long testId;
        @TableField("question_id")
        private Long questionId;
    }
}
