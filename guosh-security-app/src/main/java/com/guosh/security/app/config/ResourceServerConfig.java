package com.guosh.security.app.config;

import com.guosh.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.guosh.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.guosh.security.core.properties.SecurityConstants;
import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Author: Guosh
 * @Date: 2019-06-15 09:32
 */
@Configuration
@EnableResourceServer//资源服务器
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

    //自定义配置文件
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected AuthenticationSuccessHandler appAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler appAuthenticationFailureHandler;

    //手机号验证码登陆方式
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    //校验验证码
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    //第三方登陆
    @Autowired
    private SpringSocialConfigurer sociaSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin() //表单登陆
                .loginProcessingUrl("/api/authentication/form")//登陆
                .successHandler(appAuthenticationSuccessHandler)//自定义登陆成功
                .failureHandler(appAuthenticationFailureHandler);//自定义登陆失败;
        http
                .csrf().disable()//关闭跨站防护
                .apply(validateCodeSecurityConfig) //校验验证码
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)//手机验证码登陆
                    .and()
                .apply(sociaSecurityConfig) //第三方登陆
                    .and()
                .apply(openIdAuthenticationSecurityConfig)
                    .and()
                .requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()//下面授权配置
                    .antMatchers(
                            "/api/authentication/form", //处理登陆请求
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, //手机登陆
                        SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_OPENID,//第三方openId登陆
                        securityProperties.getBrowser().getLoginPage(), //登陆页面
                        securityProperties.getBrowser().getSignUpUrl(), //注册页面
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", //验证码
                        "/social/user",
                        "/social/signUp", //app第三方User信息
                        "/user/regist")//第三方注册跟绑定
                        .permitAll()//login请求除外不需要认证
                    .anyRequest()
                    .authenticated();//所有请求都需要身份认证
    }
}
