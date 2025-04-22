//package com.xh.online_edu_teacher.pojo.po.question;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
//import java.io.Serializable;
//
//@Data
//public abstract class BaseQuestion implements Serializable {
//    @TableId(value = "question_id",type = IdType.AUTO)
//    private Long questionId;
//    private String question;
//    private String rightAnswer;
//    private String analysis;
//    private int level;
//    @JsonIgnore
//    private Long subjectId;
//    private Long createTeacherId;
//    private Long updateTeacherId;
//}
