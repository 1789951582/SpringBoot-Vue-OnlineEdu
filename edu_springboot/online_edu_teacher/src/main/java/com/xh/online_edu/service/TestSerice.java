package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.NewQuestionDto;
import com.xh.online_edu.model.dto.NewTestDto;
import com.xh.online_edu.model.dto.ReleaseTestDto;
import com.xh.online_edu.model.dto.TestPaperDto;
import com.xh.online_edu.pojo.po.EduTest;
import com.xh.online_edu.pojo.po.question.BaseQuestion;
import com.xh.online_edu.pojo.vo.StuTest;

import java.util.List;

public interface TestSerice {
    List<StuTest> getReleaseTest(Long courseId) throws StatusFailException;

    boolean releaseTest(ReleaseTestDto dto) throws StatusFailException;

    boolean setPublic(Long testId, Integer val) throws StatusFailException;

    EduTest newTest(Long subjectId, NewTestDto dto) throws StatusFailException;

    boolean rewriteTest(NewTestDto dto) throws StatusFailException;

    List getQuestionList(Long subjectId, Integer type) throws StatusFailException;

    BaseQuestion newQuestion(Integer type, NewQuestionDto dto) throws StatusFailException;

    boolean updateQuestion(Integer type, NewQuestionDto dto) throws StatusFailException;

    boolean delQuestion(Integer type,Long questionId) throws StatusFailException;

    BaseQuestion getQuestion(Integer type, Long questionId) throws StatusFailException;

    Object commitTestPaper(TestPaperDto dto) throws StatusFailException;
}
