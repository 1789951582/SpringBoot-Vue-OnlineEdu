package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.model.resData.LoginData;
import com.xh.online_edu.pojo.po.EduTeacherInfo;

public interface UserService {

    LoginData login(LoginDto loginDto) throws StatusFailException;

    boolean register(RegisterDto registerDto)throws StatusFailException;

    EduTeacherInfo getUserInfo(Long uid)throws StatusFailException;
}
