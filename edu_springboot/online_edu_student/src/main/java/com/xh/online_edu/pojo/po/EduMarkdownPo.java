package com.xh.online_edu.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("edu_markdown")
public class EduMarkdownPo {
    @TableId(value = "markdown_id",type = IdType.AUTO)
    private Long markdownId;

    private String markdownContent;
}
