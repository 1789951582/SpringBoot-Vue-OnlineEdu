package com.xh.online_edu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterDto {
    @NotBlank(message = "用户名不能为空")
    private String nickName;
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
