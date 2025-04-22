package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class UserInformationDto extends BaseDto {
    private String srealname;
    private String sphone;
    private String sschool;
    private String scourse;
    private String snum;
    private short ssex;
    private Short sage;
}
