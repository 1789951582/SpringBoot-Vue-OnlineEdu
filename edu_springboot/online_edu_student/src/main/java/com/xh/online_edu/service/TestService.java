package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.CommitTestReq;
import com.xh.online_edu.model.res.BaseQuestionRes;
import com.xh.online_edu.model.res.CommitListRes;
import com.xh.online_edu.model.res.TestPaperRes;

public interface TestService {
    TestPaperRes getPaper(Long testId) throws StatusFailException;

    BaseQuestionRes getQuestion(Integer questionType, Long questionId) throws StatusFailException;

    boolean commitTest(Long testId, CommitTestReq data) throws StatusFailException;

    CommitListRes getCommitList(Long testId, Long uid)throws StatusFailException;

    Object getAnswerInfo(Integer type, Long questionId, Long uid)throws StatusFailException;
}
