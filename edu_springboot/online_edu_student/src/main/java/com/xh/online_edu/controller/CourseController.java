package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseReqKeys;
import com.xh.online_edu.model.dto.PageReq;
import com.xh.online_edu.service.CourseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/course")
public class CourseController {
    @Autowired
    CourseServer courseServer;

    @PostMapping("get_course_info/{courseId}")
    public Result getCourseInfo(@PathVariable Long courseId,@RequestBody BaseReqKeys baseReqKeys){
        try{
            return Result.successResponse(courseServer.getCourseInfo(baseReqKeys.getUid(),courseId));
        }catch (StatusFailException e){
            return Result.AccessDeniedResponse(e.getMessage());
        }
    }
    @PostMapping("{courseId}/get_task")
    public Result getTask(@PathVariable Long courseId){
        try{
            return Result.successResponse(courseServer.getTask(courseId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("markdown/{markdownId}")
    public Result getMarkdown(@PathVariable Long markdownId){
        try{
            return Result.successResponse(courseServer.getMarkdown(markdownId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("resource/{itemId}")
    public Result getResources(@PathVariable Long itemId){
        try{
            return Result.successResponse(courseServer.getResources(itemId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("{courseId}/get_test")
    public Result getTest(@RequestBody PageReq pageReq, @PathVariable Long courseId){
        try{
            return Result.successResponse(courseServer.getTest(pageReq,courseId));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
}
