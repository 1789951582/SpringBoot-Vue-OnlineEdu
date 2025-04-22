package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseDto;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        try{
            return Result.successResponse(userService.login(loginDto),"登录成功");
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterDto registerDto){
        try{
            return Result.successResponse(userService.register(registerDto),"注册成功");
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }

    @PostMapping("get_user_info")
    public Result getUserInfo(@RequestBody BaseDto baseDto){
        try{
            return Result.successResponse(userService.getUserInfo(baseDto.getUid()));
        }catch (StatusFailException e){
            return Result.ForbiddenResponse(e.getMessage());
        }
    }
}
