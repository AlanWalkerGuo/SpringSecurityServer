package com.guosh.security.browser.config;

import com.guosh.security.browser.authentication.BrowserAuthenticationFailureHandler;
import com.guosh.security.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.validate.code.web.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //自定义配置文件
    @Autowired
    private SecurityProperties securityProperties;

    //处理登陆成功
    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;

    //处理登陆失败
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;

    //数据源
    @Autowired
    private DataSource dataSource;

    //登陆实现
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private SessionRegistry sessionRegistry;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //密码加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //记住密码
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动自动创建persistent_logins表
        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //验证码拦截器
        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        //处理失败异常
        validateCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler);
        //把配置放进去
        validateCodeFilter.setSecurityProperties(securityProperties);
        //初始化方法
        validateCodeFilter.afterPropertiesSet();

        http
                .csrf().disable()//关闭跨站防护
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//在登陆拦截之前添加验证码拦截器
                .authorizeRequests()//下面授权配置
                    .antMatchers("/login",securityProperties.getBrowser().getLoginPage(),"/code/*").permitAll()//login请求除外不需要认证
                    .anyRequest().authenticated()//所有请求都需要身份认证
                    .and()
                .formLogin() //表单登陆
                    .loginPage("/login")//登陆判断页面
                    .loginProcessingUrl("/authentication/form")//自定义form表单登陆提交地址默认是/login
                    .successHandler(browserAuthenticationSuccessHandler)//自定义登陆成功后返回json信息
                    .failureHandler(browserAuthenticationFailureHandler)//自定义登陆失败返回json
                    .and()
                .rememberMe() //记住密码
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()) //失效时间
                    .userDetailsService(userDetailsService)
                    .and()
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).expiredUrl("/login");//用户只能登陆一次

    }
}
