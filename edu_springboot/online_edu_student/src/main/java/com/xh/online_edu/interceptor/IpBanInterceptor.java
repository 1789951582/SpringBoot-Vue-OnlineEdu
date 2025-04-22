package com.xh.online_edu.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.online_edu.common.Result;
import com.xh.online_edu.utils.IpUtils;
import com.xh.online_edu.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class IpBanInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IpUtils.getUserIpAddr(request);
        String tempObject=redisUtils.get("banIp"+ip,String.class);
        if (tempObject!=null){
            errorRes(response);
            return false;
        }
        return true;
    }

    private void errorRes(HttpServletResponse response) throws IOException {
        Result.ForbiddenResponse("当前ip已被封禁，请稍后再试");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String jsonResponse = new ObjectMapper().writeValueAsString(Result.ForbiddenResponse("当前ip已被封禁，请稍后再试"));
        response.getWriter().write(jsonResponse);
    }
}
