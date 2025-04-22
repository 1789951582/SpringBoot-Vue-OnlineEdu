package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class ChatDto extends BaseDto{
    private Long cid;
    private String msg;
    private String model;
}
