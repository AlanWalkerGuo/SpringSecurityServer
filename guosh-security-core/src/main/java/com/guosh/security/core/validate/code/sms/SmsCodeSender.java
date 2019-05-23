package com.guosh.security.core.validate.code.sms;

//短信验证码发送
public interface SmsCodeSender {
    void send(String mobile,String code);
}
