package com.xh.online_edu.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知字段
public class UpdateUserCoreDataDto extends BaseDto{
    private String oldEmail;
    private String pwd;
    private String email;
    private String code;
}
