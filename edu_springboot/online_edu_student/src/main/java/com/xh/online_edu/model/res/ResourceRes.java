package com.xh.online_edu.model.res;

import com.xh.online_edu.pojo.po.EduResourcesPo;
import lombok.Data;

import java.util.List;

@Data
public class ResourceRes {
    private Long itemId;
    private String itemTitle;
    private List<EduResourcesPo> resourcePoList;

}
