package com.xh.online_edu.service;

import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.UpdateUserCoreDataDto;
import com.xh.online_edu.model.dto.UserInformationDto;

public interface UserService {
    boolean modifyInformation(UserInformationDto dto) throws StatusFailException;

    boolean getCode(Long uid,String email) throws StatusFailException;

    boolean changePassword(UpdateUserCoreDataDto dto) throws StatusFailException;

    boolean changeEmail(UpdateUserCoreDataDto dto) throws StatusFailException;
}
