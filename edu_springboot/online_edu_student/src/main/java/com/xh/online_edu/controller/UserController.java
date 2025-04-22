package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseDto;
import com.xh.online_edu.model.dto.UpdateUserCoreDataDto;
import com.xh.online_edu.model.dto.UserInformationDto;
import com.xh.online_edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/modify_information")
    public Result modifyInformation(@RequestBody UserInformationDto dto){
        try{
            return Result.successResponse(userService.modifyInformation(dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("/get_code")
    public Result getCode(@RequestBody UpdateUserCoreDataDto dto){
        try{
            return Result.successResponse(userService.getCode(dto.getUid(),dto.getOldEmail()));
        }catch (StatusFailException e){
            return Result.FailResponse("获取失败");
        }
    }

    @PostMapping("/change_password")
    public Result changePassword(@RequestBody UpdateUserCoreDataDto dto){
        try {
            return Result.successResponse(userService.changePassword(dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }

    @PostMapping("/change_email")
    public Result changeEmail(@RequestBody UpdateUserCoreDataDto dto){
        try {
            return Result.successResponse(userService.changeEmail(dto));
        }catch (StatusFailException e){
            return Result.FailResponse(e.getMessage());
        }
    }
}
