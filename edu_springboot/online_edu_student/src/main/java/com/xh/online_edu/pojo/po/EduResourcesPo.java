package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("edu_resources")
public class EduResourcesPo {
    private Long resourceId;
    private String resourceUrl;
    private String resourceTitle;
    private Short typeId;
    @JsonIgnore
    private Long itemId;
}
