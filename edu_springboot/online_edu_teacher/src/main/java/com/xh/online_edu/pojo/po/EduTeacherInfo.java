package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("edu_teacher_info")
public class EduTeacherInfo {
    @TableId(value = "t_uid",type = IdType.AUTO)
    private Long tUid;
    private String tNickname;
    @JsonIgnore
    private String tPwd;
    @JsonIgnore
    private String tRealname;
    @JsonIgnore
    private String tEmail;
    private String tAvatar;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
