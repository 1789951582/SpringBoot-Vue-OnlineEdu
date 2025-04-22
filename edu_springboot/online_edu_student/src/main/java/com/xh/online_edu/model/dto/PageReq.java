package com.xh.online_edu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: XiaoHao
 * @Date: 2025/1/29 17:11
 * @Description: 获取分页内容请求实体类
 */

@Data
public class PageReq extends BaseReqKeys{
    @NotBlank(message = "uid不能为空")
    private Long uid;
    @NotBlank(message = "pageNum不能为空")
    private Integer pageNum;
    @NotBlank(message = "pageSize不能为空")
    private Integer pageSize;
}
