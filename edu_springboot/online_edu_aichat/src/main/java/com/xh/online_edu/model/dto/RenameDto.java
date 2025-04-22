package com.xh.online_edu.model.dto;

import lombok.Data;

@Data
public class RenameDto extends BaseDto{
    private Long cid;
    private String newName;
}
