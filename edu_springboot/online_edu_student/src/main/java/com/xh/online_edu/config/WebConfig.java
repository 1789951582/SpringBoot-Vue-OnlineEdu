package com.xh.online_edu.config;

import com.xh.online_edu.interceptor.IpBanInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IpBanInterceptor())
                .addPathPatterns("/api/passport/**");
    }
}
