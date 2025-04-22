package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseReqKeys;
import com.xh.online_edu.model.dto.CommitTestReq;
import com.xh.online_edu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping("get_test_paper/{testId}")
    public Result getPaper(@PathVariable Long testId){
        try{
            return Result.successResponse(testService.getPaper(testId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
    @PostMapping("get_question/{questionType}/{questionId}")
    public Result getQuestion(@PathVariable Integer questionType,@PathVariable Long questionId){
        try{
            return Result.successResponse(testService.getQuestion(questionType,questionId));
        }catch (StatusFailException e){
            return Result.SystemErrorResponse(e.getMessage());
        }
    }

    @PostMapping("commit_test/{testId}")
    public Result commitTest(@PathVariable Long testId, @RequestBody CommitTestReq data){
        try{
            return Result.successResponse(testService.commitTest(testId,data),"提交成功");
        }catch (StatusFailException e){
            return Result.SystemErrorResponse(e.getMessage());
        }
    }

    @PostMapping("commitList/{testId}")
    public Result getCommitList(@PathVariable Long testId, @RequestBody BaseReqKeys baseReqKeys){
        try{
            return Result.successResponse(testService.getCommitList(testId,baseReqKeys.getUid()));
        }catch (StatusFailException e){
            return Result.SystemErrorResponse(e.getMessage());
        }
    }

    @PostMapping("get_answer_info/{type}/{questionId}")
    public Result getAnswerInfo(@PathVariable Integer type,@PathVariable Long questionId, @RequestBody BaseReqKeys dto){
        try{
            return Result.successResponse(testService.getAnswerInfo(type,questionId,dto.getUid()));
        }catch (StatusFailException e){
            return Result.SystemErrorResponse(e.getMessage());
        }
    }
}
