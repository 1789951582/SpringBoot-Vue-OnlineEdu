package com.xh.online_edu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: XiaoHao
 * @Date: 2025/1/29 17:11
 * @Description: 注册数据实体类
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class RegisterDto implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    private String pwd;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String emailAddr;

    @NotBlank(message = "验证码不能为空")
    private String emailCode;
}
