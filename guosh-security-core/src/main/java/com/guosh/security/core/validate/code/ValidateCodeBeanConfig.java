package com.guosh.security.core.validate.code;

import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.validate.code.image.ImageCodeGenerator;
import com.guosh.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.guosh.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 图片验证码图片生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")//在spring加载bean时会先查找有没有名字为imageCodeGenerator
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator codeGenerator=new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    /**
     * 短信验证码发送器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "SmsCodeSender")//在spring加载bean时会先查找有没有名字为SmsCodeSender
    public SmsCodeSender SmsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
