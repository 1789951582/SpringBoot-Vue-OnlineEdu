package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.*;
import com.xh.online_edu.service.TestSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher/test")
public class TestController {

    @Autowired
    TestSerice testSerice;

    @PostMapping("get_release_test/{courseId}")
    public Result getReleaseTest(@PathVariable Long courseId){
        try{
            return Result.successResponse(testSerice.getReleaseTest(courseId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("release_test")
    public Result releaseTest(@RequestBody ReleaseTestDto dto){
        try{
            return Result.successResponse(testSerice.releaseTest(dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
    @PostMapping("setPublic/{testId}/{val}")
    public Result setPublic(@PathVariable Long testId,@PathVariable Integer val){
        try{
            return Result.successResponse(testSerice.setPublic(testId,val));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("{subjectId}/new_test")
    public Result newTest(@PathVariable Long subjectId,@RequestBody NewTestDto dto){
        try{
            return Result.successResponse(testSerice.newTest(subjectId,dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("rewrite_test")
    public Result rewriteTest(@RequestBody NewTestDto dto){
        try{
            return Result.successResponse(testSerice.rewriteTest(dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("{subjectId}/get_question_list/{type}")
    public Result getQuestionList(@PathVariable Long subjectId, @PathVariable Integer type, @RequestBody BaseDto dto){
        try{
            return Result.successResponse(testSerice.getQuestionList(subjectId,type));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("new_question/{type}")
    public Result newQuestion(@PathVariable Integer type,@RequestBody NewQuestionDto dto){
        try{
            return Result.successResponse(testSerice.newQuestion(type, dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("update_question/{type}")
    public Result updateQuestion(@PathVariable Integer type,@RequestBody NewQuestionDto dto){
        try{
            return Result.successResponse(testSerice.updateQuestion(type, dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("del_question/{type}/{questionId}")
    public Result delQuestion(@PathVariable Integer type,@PathVariable Long questionId, @RequestBody BaseDto dto) {
        try {
            return Result.successResponse(testSerice.delQuestion(type,questionId));
        } catch (StatusFailException e) {
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("get_question/{type}/{questionId}")
    public Result getQuestion(@PathVariable Integer type,@PathVariable Long questionId){
        try{
            return Result.successResponse(testSerice.getQuestion(type, questionId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("commit_test_paper")
    public Result commitTestPaper(@RequestBody TestPaperDto dto){
        try{
            return Result.successResponse(testSerice.commitTestPaper(dto));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
}
