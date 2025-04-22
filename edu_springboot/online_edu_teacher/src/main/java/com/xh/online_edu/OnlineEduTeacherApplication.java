package com.xh.online_edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync(proxyTargetClass=true) //开启异步注解
@SpringBootApplication
@EnableTransactionManagement
public class OnlineEduTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineEduTeacherApplication.class, args);
    }

}
