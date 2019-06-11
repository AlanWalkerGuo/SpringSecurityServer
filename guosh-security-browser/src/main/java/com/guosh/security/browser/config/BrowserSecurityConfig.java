package com.guosh.security.browser.config;

import com.guosh.security.core.authentication.FormAuthenticationConfig;
import com.guosh.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.guosh.security.core.properties.SecurityConstants;
import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * 浏览器安全配置主类
 * @Author: Guosh
 * @Date: 2019-05-28 15:10
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    //自定义配置文件
    @Autowired
    private SecurityProperties securityProperties;

    //登陆相关配置
    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    //校验验证码
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    //手机号验证码登陆方式
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    //登陆实现
    @Autowired
    private UserDetailsService userDetailsService;

    //第三方登陆
    @Autowired
    private SpringSocialConfigurer sociaSecurityConfig;

    //数据源
    @Autowired
    private DataSource dataSource;

    //处理session失效
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    //处理最大登陆数
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //集群session处理
    @Autowired
    private SessionRegistry sessionRegistry;
    //退出处理请求
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    //记住密码
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动自动创建persistent_logins表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http);
        http
                .csrf().disable()//关闭跨站防护
                .apply(validateCodeSecurityConfig) //校验验证码
                    .and()
                .apply(smsCodeAuthenticationSecurityConfig)//手机验证码登陆
                    .and()
                .apply(sociaSecurityConfig) //第三方登陆
                    .and()
                .authorizeRequests()//下面授权配置
                    .antMatchers(
                            SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, //处理登陆请求
                            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, //手机登陆
                            securityProperties.getBrowser().getLoginPage(), //登陆页面
                            securityProperties.getBrowser().getSignUpUrl(), //注册页面
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*", //验证码
                            "/user/regist")//第三方注册跟绑定
                            .permitAll()//login请求除外不需要认证
                    .anyRequest()
                    .authenticated()//所有请求都需要身份认证
                    .and()
                .rememberMe() //记住密码
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()) //失效时间
                    .userDetailsService(userDetailsService)
                    .and()
                .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy) //session失效后的处理
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions()) //用户最大登陆数
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//是否阻止登陆
                    .expiredUrl(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)//用户只能登陆一次
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)//用户被挤掉后的处理
                    .sessionRegistry(sessionRegistry)
                    .and()
                    .and()
                .logout()
                    .logoutUrl("/sigOut")//默认退出路径是logOut可以自定义
                    .logoutSuccessHandler(logoutSuccessHandler)//处理退出到类
                   //.logoutSuccessUrl("/login")//退出后跳到到页面
                    .deleteCookies("JSESSIONID");


    }
}
