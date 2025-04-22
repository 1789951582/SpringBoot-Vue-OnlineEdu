package com.xh.online_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.mapper.po.EduTeacherInfoMapper;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.model.resData.LoginData;
import com.xh.online_edu.pojo.po.EduTeacherInfo;
import com.xh.online_edu.service.UserService;
import com.xh.online_edu.utils.JwtUtils;
import com.xh.online_edu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    EduTeacherInfoMapper eduTeacherInfoMapper;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public LoginData login(LoginDto loginDto) throws StatusFailException {
        QueryWrapper<EduTeacherInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("t_uid",loginDto.getAccount()).or().eq("t_nickname",loginDto.getAccount());
        EduTeacherInfo teacherInfo=eduTeacherInfoMapper.selectOne(queryWrapper);
        if (teacherInfo.getTPwd().equals(loginDto.getPwd())){
            //生成token
            String uid=teacherInfo.getTUid().toString();
            String token=jwtUtils.generateToken(uid);
            redisUtils.set("Ttoken"+uid,token);
            LoginData res=new LoginData();
            res.setUid(teacherInfo.getTUid());
            res.setToken(token);
            return res;
        }
        throw new StatusFailException("密码错误");
    }

    @Override
    public boolean register(RegisterDto registerDto) throws StatusFailException{
        QueryWrapper<EduTeacherInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("t_uid",registerDto.getNickName()).or().eq("t_nickname",registerDto.getNickName());
        long count=eduTeacherInfoMapper.selectCount(queryWrapper);
        if (count>0){
            throw new StatusFailException("用户名已存在");
        }
        EduTeacherInfo teacherInfo=new EduTeacherInfo();
        teacherInfo.setTNickname(registerDto.getNickName());
        teacherInfo.setTPwd(registerDto.getPwd());
        int hasSuccess=eduTeacherInfoMapper.insert(teacherInfo);
        if (hasSuccess<0){
            throw new StatusFailException("注册失败");
        }
        return true;
    }

    @Override
    public EduTeacherInfo getUserInfo(Long uid) throws StatusFailException{
        EduTeacherInfo tchInfo=eduTeacherInfoMapper.selectById(uid);
        if (tchInfo==null){
            throw new StatusFailException("没找到指定用户");
        }
        return tchInfo;
    }
}
