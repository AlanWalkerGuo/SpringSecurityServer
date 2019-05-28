package com.guosh.security.core.authentication;

import com.guosh.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: Guosh
 * @Date: 2019-05-28 15:19
 */
@Component
public class FormAuthenticationConfig{

    @Autowired
    protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin() //表单登陆
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//登陆判断页面
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)//自定义form表单登陆提交地址默认是/login
                .successHandler(imoocAuthenticationSuccessHandler)//自定义登陆成功
                .failureHandler(imoocAuthenticationFailureHandler);//自定义登陆失败
    }

}
