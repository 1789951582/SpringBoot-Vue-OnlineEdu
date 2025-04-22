//package com.xh.online_edu.pojo.po.question;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//
//import java.io.Serializable;
//
//@Data
//public abstract class BaseQuestionPo implements Serializable {
//    @TableId(type = IdType.AUTO)
//    private Long questionId;
//    private String question;
//    private String rightAnswer;
//    private String analysis;
//    private Integer level;
//    @JsonIgnore
//    private Long subjectId;
//    @JsonIgnore
//    private Long createTeacherId;
//    @JsonIgnore
//    private Long updateTeacherId;
//}
