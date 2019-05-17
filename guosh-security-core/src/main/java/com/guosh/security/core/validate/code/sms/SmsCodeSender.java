package com.guosh.security.core.validate.code.sms;

//短信发送
public interface SmsCodeSender {
    void send(String mobile,String code);
}
