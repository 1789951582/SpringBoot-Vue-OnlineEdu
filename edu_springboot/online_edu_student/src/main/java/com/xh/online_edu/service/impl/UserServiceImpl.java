package com.xh.online_edu.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.SecureUtil;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.StuInfoPoMapper;
import com.xh.online_edu.model.dto.UpdateUserCoreDataDto;
import com.xh.online_edu.model.dto.UserInformationDto;
import com.xh.online_edu.service.UserService;
import com.xh.online_edu.utils.EmailUtils;
import com.xh.online_edu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    StuInfoPoMapper stuInfoPoMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EmailUtils emailUtils;

    @Override
    public boolean modifyInformation(UserInformationDto dto) throws StatusFailException {
        int isOk=stuInfoPoMapper.modifyInformation(dto);
        if (isOk<0){
            throw new StatusFailException("更新失败");
        }
        return true;
    }

    @Override
    public boolean getCode(Long uid,String email) throws StatusFailException {
        String emailAddr=stuInfoPoMapper.getUserEmail(uid);
        if (!email.equals(emailAddr)){
            throw new StatusFailException("邮箱验证出错,请输入正确的旧邮箱");
        }
        String lockKey = "register-email-lock" + emailAddr;
        if (redisUtils.hasKey(lockKey)){
            throw new StatusFailException("对不起，您的操作频率过快，请在"+redisUtils.getExpire(lockKey)+"秒后继续");
        }
        String generateNumericCode=generateNumericCode();
        DateTime expireTime = DateUtil.offsetMinute(new Date(), 10);
        redisUtils.set("EmailCord"+uid,generateNumericCode,10*60);
        emailUtils.sendRegisterCode(emailAddr,generateNumericCode,expireTime);
        redisUtils.set(lockKey,0,60);
        return true;
    }

    @Override
    public boolean changePassword(UpdateUserCoreDataDto dto) throws StatusFailException {
        String code=redisUtils.get("EmailCord"+dto.getUid(),String.class);
        if (code==null){
            throw new StatusFailException("验证码不存在或已过期");
        }
        if (!StringUtils.hasText(dto.getPwd())) {
            throw new StatusFailException("密码不能为空");
        }

        if (dto.getPwd().length() < 6 || dto.getPwd().length() > 20) {
            throw new StatusFailException("密码长度应该为6~20位！");
        }
        int isOk=stuInfoPoMapper.updateUserPwd(dto.getUid(), SecureUtil.md5(dto.getPwd()));
        if (isOk<0){
            throw new StatusFailException("修改失败");
        }
        return true;
    }

    @Override
    public boolean changeEmail(UpdateUserCoreDataDto dto) throws StatusFailException {
        String code=redisUtils.get("EmailCord"+dto.getUid(),String.class);
        if (code==null){
            throw new StatusFailException("验证码不存在或已过期");
        }
        if (!Validator.isEmail(dto.getEmail().trim())){
            throw new StatusFailException("对不起！您的邮箱格式不正确！");
        }
        int isOk=stuInfoPoMapper.updateUserEmail(dto.getUid(), dto.getEmail());
        if (isOk<0){
            throw new StatusFailException("修改失败");
        }
        return true;
    }

    /**
     * 生成指定数量的随机整数。
     *
     * @return 包含生成的随机数的字符串。
     */
    private static String generateNumericCode() {
        SecureRandom rand = new SecureRandom();
        // 生成4位随机数字验证码
        return String.format("%04d", rand.nextInt(10000));
    }
}
