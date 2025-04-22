package com.xh.online_edu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: XiaoHao
 * @Date: 2025/1/29 17:11
 * @Description: 登录数据实体类
 */
@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String pwd;
}
