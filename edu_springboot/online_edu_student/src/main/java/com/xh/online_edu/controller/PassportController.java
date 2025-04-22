package com.xh.online_edu.controller;

import com.xh.online_edu.common.Result;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.BaseReqKeys;
import com.xh.online_edu.model.dto.EmailDto;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.service.PassportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 小皓
 * @Date: 2025/1/28 20:56
 * @Description: 处理登录、注册、重置密码
 */

@RestController
@RequestMapping("/api/passport")
public class PassportController {

    @Autowired
    private PassportService passportService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request){
        try{
            return Result.successResponse(passportService.login(loginDto,request),"登录成功");
        }catch (StatusFailException e){
            return Result.AccessDeniedResponse(e.getMessage());
        }
    }

    @GetMapping("check_nickname")
    public Result checkNickName(String nickname,HttpServletRequest request){
        try{
            return Result.successResponse(passportService.checkNickName(nickname,request));
        }catch (StatusFailException e){
            return Result.SystemErrorResponse(e.getMessage());
        }
    }

    @PostMapping("get_register_code")
    public Result getRegisterCode(HttpServletRequest request){
        try{
            return Result.successResponse(passportService.getRegisterCode(request));
        }
        catch (StatusFailException e){
            return Result.AccessDeniedResponse(e.getMessage());
        }
    }

    @PostMapping("getEmailCode")
    public Result getRegisterEmailCode(@RequestBody EmailDto emailDto,HttpServletRequest request){
        try{
            return Result.successResponse(passportService.getRegisterEmailCode(emailDto.getEmailAddr(),emailDto.getCodeId(),emailDto.getDatas(),request),"ok");
        }catch (StatusFailException e){
            return Result.AccessDeniedResponse(e.getMessage());
        }
    }

    @PostMapping("register")
    public Result register(@Validated @RequestBody RegisterDto registerDto,HttpServletRequest request){
        try{
            return Result.successResponse(passportService.register(registerDto,request),"注册成功");
        }catch (StatusFailException e){
            return Result.AccessDeniedResponse(e.getMessage());
        }
    }

    @PostMapping("getstuinfo")
    public Result getStuinfo(@RequestBody BaseReqKeys baseReqKeys){
        try{
            return Result.successResponse(passportService.getStuinfo(baseReqKeys.getUid()), baseReqKeys.getToken());
        }catch (StatusFailException e){
            return Result.NotFound(e.getMessage());
        }
    }
}
