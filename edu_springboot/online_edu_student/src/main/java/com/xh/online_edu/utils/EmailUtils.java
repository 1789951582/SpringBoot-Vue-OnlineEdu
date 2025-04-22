package com.xh.online_edu.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.UnicodeUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Date;

@Component
@Slf4j
public class EmailUtils {
    @Value("${web.name}")
    private String web_name;
    @Value("${web.short_name}")
    private String web_short_name;
    @Value("${web.url}")
    private String web_url;
    @Value("${web.logo_url}")
    private String web_logo_url;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSenderImpl sender;

//    @Getter
//    @AllArgsConstructor
//    public enum EmailConstant{
//        WEB_NAME("WEB_NAME"),
//        WEB_SHORT_NAME("WEB_SHORT_NAME"),
//        WEB_URL("WEB_URL"),
//        WEB_LOGO_URL("WEB_LOGO_URL");
//        private String value;
//    }

    /**
     * @param email 用户邮箱
     * @param code  生成的六位随机数字验证码
     * @MethodName sendRegisterCode
     * @Description 为正在注册的用户发送一份注册验证码。
     * @Return
     * @Since 2021/1/14
     */
    @Async
    public void sendRegisterCode(String email,String code,DateTime expireTime){
//        DateTime expireTime = DateUtil.offsetMinute(new Date(), 10);
        MimeMessage mimeMessage=sender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置渲染到html页面对应的值
            Context context = new Context();
            context.setVariable("WEB_NAME",this.web_name);
            context.setVariable("WEB_SHORT_NAME",this.web_short_name);
            context.setVariable("WEB_URL",this.web_url);
            context.setVariable("WEB_LOGO_URL",this.web_logo_url);
            context.setVariable("CODE", code);
            context.setVariable("EXPIRE_TIME", expireTime.toString());

            //利用模板引擎加载html文件进行渲染并生成对应的字符串
            String emailContent = templateEngine.process("emailTemplate_registerCode", context);

            // 设置邮件标题
            mimeMessageHelper.setSubject(UnicodeUtil.toString("") + "的注册邮件");
            mimeMessageHelper.setText(emailContent, true);
            // 收件人
            mimeMessageHelper.setTo(email);
            // 发送人
            mimeMessageHelper.setFrom(sender.getUsername());

            sender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("用户注册的邮件任务发生异常");
            log.error("用户注册的邮件任务发生异常", e.getMessage());
        }
    }
}
