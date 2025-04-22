package com.xh.online_edu.model.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class CommitTestReq extends BaseReqKeys{
//    private Map<QuestionType, Map<Long,String>> answerMap;
private Map<Long, String> answerMap;

//    @Data
//    public class CommitResult {
//        private int totalScore;
//        private Long commitId;
//        private LocalDateTime commitTime;
//    }
}
