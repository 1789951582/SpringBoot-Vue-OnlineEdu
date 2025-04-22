package com.xh.online_edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableRetry
@EnableScheduling // 开启定时任务
//@EnableDiscoveryClient // 开启注册发现
@SpringBootApplication
@EnableAsync(proxyTargetClass=true) //开启异步注解
@EnableTransactionManagement
public class OnlineEduStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineEduStudentApplication.class, args);
    }

}
