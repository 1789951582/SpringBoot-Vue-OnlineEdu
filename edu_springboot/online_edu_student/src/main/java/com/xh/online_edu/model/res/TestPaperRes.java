package com.xh.online_edu.model.res;

import com.xh.online_edu.model.enums.QuestionType;
import com.xh.online_edu.pojo.po.EduTestPaperPo;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class TestPaperRes {
    private Long testId;
//    private int questionTotal;//前端算
    private LinkedHashMap<QuestionType,List<EduTestPaperPo>> questionMap=new LinkedHashMap<>();

    public void putQuestionMap(QuestionType key,List<EduTestPaperPo> item){
        questionMap.put(key,item);
    }

}
