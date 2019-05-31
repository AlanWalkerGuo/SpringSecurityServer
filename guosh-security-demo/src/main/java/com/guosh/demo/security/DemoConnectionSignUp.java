package com.guosh.demo.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @Author: Guosh
 * @Date: 2019-05-31 15:49
 * 次组建会自动第三方登陆免注册
 */

public class DemoConnectionSignUp implements ConnectionSignUp {

    @Override
    public String execute(Connection<?> connection) {
        return connection.getKey().getProviderUserId();
    }
}
