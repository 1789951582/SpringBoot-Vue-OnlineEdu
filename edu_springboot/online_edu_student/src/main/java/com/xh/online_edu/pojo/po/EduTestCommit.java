package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EduTestCommit {

    @TableId(type = IdType.AUTO)
    private Long commitId;
    private Double score;
    private Long testId;
    private Long sUid;
    private LocalDateTime createTime;

}
