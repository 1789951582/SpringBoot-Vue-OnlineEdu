package com.xh.online_edu.model.dto;

import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduTestPaper;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class TestPaperDto extends BaseDto{
    private Long testId;
    private LinkedHashMap<QuestionType, List<EduTestPaper>> questionMap=new LinkedHashMap<>();
    private List<Long> delQueIdList;
}
