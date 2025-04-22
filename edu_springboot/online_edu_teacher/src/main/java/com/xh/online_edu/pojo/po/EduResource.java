package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("edu_resources")
public class EduResource {
    @TableId(value = "resource_id",type = IdType.INPUT)
    private Long resourceId;
    private String resourceUrl;
    private String resourceTitle;
    private Integer typeId;
    private Long itemId;
}
