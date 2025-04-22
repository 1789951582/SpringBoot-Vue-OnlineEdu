package com.xh.online_edu.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String pwd;

    private String emailAddr;
    private String codeId;
}
