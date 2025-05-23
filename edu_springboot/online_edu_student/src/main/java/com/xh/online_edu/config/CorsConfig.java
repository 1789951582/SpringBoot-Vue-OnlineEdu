package com.xh.online_edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许跨域访问的路径
                .allowedOriginPatterns("*")//允许跨域的源
                .allowedMethods("GET","POST")//允许请求类型
                .maxAge(168000)//预检测时间
                .allowedHeaders("*")//允许头部设置
                .allowCredentials(true);//是否发送cookie
    }
}
