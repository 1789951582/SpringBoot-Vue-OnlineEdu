package com.xh.online_edu.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageRes<T> {
    private List<T> records;
    private int total;
    private Integer pageNum;
    private Integer pageSize;
    public Integer getPages() {
        return (int) Math.ceil((double) total / pageSize);
    }
}
