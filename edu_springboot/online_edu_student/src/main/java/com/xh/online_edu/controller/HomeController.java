package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.PageReq;
import com.xh.online_edu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    @PostMapping("/getCourseList")
    public Result getCourseList(@RequestBody PageReq pageReq,@RequestParam short status){
        try{
            return Result.successResponse(homeService.getCourseList(pageReq.getUid(),pageReq.getPageNum(),pageReq.getPageSize(),status));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
}
