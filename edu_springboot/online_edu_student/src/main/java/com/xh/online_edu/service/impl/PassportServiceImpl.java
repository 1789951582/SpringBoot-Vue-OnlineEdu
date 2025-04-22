package com.xh.online_edu.service.impl;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.CaptchaResponse;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xh.online_edu.mapper.po.StuInfoPoMapper;
import com.xh.online_edu.common.exception.StatusFailException;
import com.xh.online_edu.model.dto.RegisterDto;
import com.xh.online_edu.pojo.po.StuInfoPo;
import com.xh.online_edu.utils.EmailUtils;
import com.xh.online_edu.utils.IpUtils;
import com.xh.online_edu.utils.JwtUtils;
import com.xh.online_edu.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.xh.online_edu.model.dto.LoginDto;
import com.xh.online_edu.service.PassportService;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.SecureUtil;

import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PassportServiceImpl implements PassportService {

    @Autowired
    StuInfoPoMapper stuInfoPoMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ConcurrentHashMap<String,String> login(LoginDto loginDto,HttpServletRequest request) throws StatusFailException {
        loginDto.setPwd(loginDto.getPwd().trim());
        loginDto.setAccount(loginDto.getAccount().trim());
        String ip=IpUtils.getUserIpAddr(request);

        if (!StringUtils.hasText(loginDto.getAccount()) || !StringUtils.hasText(loginDto.getPwd())){
            this.ipError(ip);
            throw new StatusFailException("用户名或密码不能为空！",ip);//因为会在前端做合法判定，一般不会触发
        }
        if (loginDto.getPwd().length()<6 || loginDto.getPwd().length()>20){
            this.ipError(ip);
            throw new StatusFailException("密码长度应该为6~20位！",ip);
        }
        if (!Validator.isEmail(loginDto.getAccount().trim())){
            if (loginDto.getAccount().length() > 20) {
                this.ipError(ip);
                throw new StatusFailException("用户名非法!",ip);
            }
        }
        String tempObject =redisUtils.get("banAccount"+loginDto.getAccount(),String.class);
        if (tempObject!=null){
            this.ipError(ip);
            throw new StatusFailException("当前账户存在风险，禁止登录30分钟!");
        }

        StuInfoPo stuInfoPo =stuInfoPoMapper.getStu(loginDto.getAccount());

        if (stuInfoPo==null) {
            //查找不到
            this.ipError(ip);
            throw new StatusFailException("找不到该用户!");
        }
        //查找到用户
        if (!stuInfoPo.getSPwd().equals(SecureUtil.md5(loginDto.getPwd()))){
            //密码错误
            this.ipError(ip);
            this.accountLoginError(loginDto.getAccount());
            throw new StatusFailException("密码错误!");
        }
        //密码正确
        this.cleanipError(ip);
        String uid=stuInfoPo.getSUid().toString();
        String token=jwtUtils.generateToken(uid);
        redisUtils.set("Stoken"+uid,token,86400*7);
        ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();
        map.put("uid",uid);
        map.put("token",token);
        return map;
    }

    @Override
    public boolean checkNickName(String nickname, HttpServletRequest request) throws StatusFailException {
        String ip=IpUtils.getUserIpAddr(request);
        //查询一次用户表视作一次登录失败
        ipError(ip);
        StuInfoPo stuInfoPo=stuInfoPoMapper.getStu(nickname);
        if (stuInfoPo!=null){
            throw new StatusFailException("该用户名已被占用");
        }
        return true;
    }

    @Override
    public StuInfoPo getStuinfo(Long uid) throws StatusFailException {
        StuInfoPo stuInfoPo=stuInfoPoMapper.selectById(uid);
        if (stuInfoPo==null){
            throw new StatusFailException("未找到指定数据,请联系系统管理员");
        }
        return stuInfoPo;
    }

    @Override
    public CaptchaResponse<ImageCaptchaVO> getRegisterCode(HttpServletRequest request) throws StatusFailException{
        String ip=IpUtils.getUserIpAddr(request);
        Integer tempObject=redisUtils.get("captcha"+ip,Integer.class);
        if (tempObject==null)
            redisUtils.set("captcha"+ip,1,1800);
        else {
            int captchaCount=tempObject;
            if (captchaCount>8){
                redisUtils.expire("captcha"+ip,1800);
                this.ipError(ip);
                throw new StatusFailException("获取验证码次数过多，请稍后再试");
            }
            redisUtils.set("captcha"+ip,++captchaCount,1800);
        }
        return imageCaptchaApplication.generateCaptcha(CaptchaTypeConstant.SLIDER);
    }

    @Override
    public boolean getRegisterEmailCode(String emailAddr, String codeId, ImageCaptchaTrack sliderCaptchaTrack, HttpServletRequest request) throws StatusFailException {
        String ip=IpUtils.getUserIpAddr(request);
        if (!Validator.isEmail(emailAddr.trim())){
            this.ipError(ip);
            throw new StatusFailException("对不起！您的邮箱格式不正确！");
        }
        String lockKey = "register-email-lock" + emailAddr;
        if (redisUtils.hasKey(lockKey)){
            this.ipError(ip);
            throw new StatusFailException("对不起，您的操作频率过快，请在"+redisUtils.getExpire(lockKey)+"秒后继续");
        }
        QueryWrapper<StuInfoPo> wrapper=new QueryWrapper();
        wrapper.eq("s_email",emailAddr);
        Long count = stuInfoPoMapper.selectCount(wrapper);
//        StuInfoPo stuInfoPo=stuInfoPoMapper.eq("s_email",emailAddr);
        if (count>0){
            this.ipError(ip);
            throw new StatusFailException("对不起！该邮箱已被注册，请更换新的邮箱！");
        }
        ApiResponse<?> match = imageCaptchaApplication.matching(codeId,sliderCaptchaTrack);
        if (!match.isSuccess()) {
//            this.ipError(ip);
            return false;
        }
        String generateNumericCode=generateNumericCode();
        DateTime expireTime = DateUtil.offsetMinute(new Date(), 10);
        redisUtils.set("EmailCord"+emailAddr,generateNumericCode,10*60);
        emailUtils.sendRegisterCode(emailAddr,generateNumericCode,expireTime);
        redisUtils.set(lockKey,0,60);
        return true;
    }

    @Override
    public boolean register(RegisterDto registerDto, HttpServletRequest request) throws StatusFailException {
        String ip=IpUtils.getUserIpAddr(request);
        String tempObject=redisUtils.get("EmailCord"+registerDto.getEmailAddr(),String.class);
        if (tempObject==null){
            ipError(ip);
            throw new StatusFailException("验证码不存在或已过期");
        }
        if (!registerDto.getEmailCode().equals(tempObject.toString())){
            ipError(ip);
            throw new StatusFailException("验证码不正确");
        }
        if (!StringUtils.hasText(registerDto.getPwd())) {
            ipError(ip);
            throw new StatusFailException("密码不能为空");
        }

        if (registerDto.getPwd().length() < 6 || registerDto.getPwd().length() > 20) {
            ipError(ip);
            throw new StatusFailException("密码长度应该为6~20位！");
        }

        if (!StringUtils.hasText(registerDto.getNickname())) {
            ipError(ip);
            throw new StatusFailException("用户名不能为空");
        }

        if (registerDto.getNickname().length() > 20) {
            ipError(ip);
            throw new StatusFailException("用户名长度不能超过20位!");
        }
        StuInfoPo stuInfoPo=new StuInfoPo();
        stuInfoPo.setSNickname(registerDto.getNickname().trim());
        stuInfoPo.setSPwd(SecureUtil.md5(registerDto.getPwd().trim()));
        stuInfoPo.setSEmail(registerDto.getEmailAddr().trim());
        int addUser=stuInfoPoMapper.insert(stuInfoPo);
        if (addUser!=1)
            throw new StatusFailException("注册失败，请稍后重新尝试！");
        return true;
    }



    private void ipError(String ip) throws StatusFailException{
        Integer tempObject=redisUtils.get("ipError"+ip,Integer.class);
        if (tempObject==null){
            redisUtils.set("ipError"+ip,1,600);
            return;
        }
        int errorCount=tempObject;
        if (errorCount>20){
            redisUtils.set("banIp"+ip,ip,1800);
            redisUtils.del("ipError"+ip);
            throw new StatusFailException("当前ip请求过多,已被封禁半小时",ip);
        }
        redisUtils.set("ipError"+ip,++errorCount,600);
    }

    private void cleanipError(String ip){
        redisUtils.del("ipError"+ip);
    }

    private void accountLoginError(String account){
        Integer tempObject=redisUtils.get("accountLoginError"+account,Integer.class);
        if (tempObject==null){
            redisUtils.set("accountLoginError"+account,1,1800);
            return;
        }
        int errorCount=tempObject;
        if (errorCount>8){
            redisUtils.set("banAccount"+account,account,1800);
            redisUtils.del("accountLoginError"+account);
        }
        redisUtils.set("accountLoginError"+account,++errorCount,1800);
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

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
