package com.xh.online_edu.service;

import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.pojo.po.StuInfoPo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public interface PassportService {
    ConcurrentHashMap<String,String> login(LoginDto loginDto, HttpServletRequest request) throws StatusFailException;

    CaptchaResponse<ImageCaptchaVO> getRegisterCode(HttpServletRequest request) throws StatusFailException;

    boolean getRegisterEmailCode(String emailAddr, String codeId, ImageCaptchaTrack sliderCaptchaTrack, HttpServletRequest request)throws StatusFailException;

    boolean register(RegisterDto registerDto, HttpServletRequest request)throws StatusFailException;

    boolean checkNickName(String nickname, HttpServletRequest request)throws StatusFailException;

    StuInfoPo getStuinfo(Long uid)throws StatusFailException;
}
