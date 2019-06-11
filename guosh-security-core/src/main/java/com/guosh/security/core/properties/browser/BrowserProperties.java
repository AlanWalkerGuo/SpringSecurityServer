package com.guosh.security.core.properties.browser;

import com.guosh.security.core.properties.SecurityConstants;

public class BrowserProperties {
    //session配置项
    private SessionProperties session = new SessionProperties();
    //自定义登陆页面
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL; //如果用户没有配置登陆页面走默认

    //自定义注册页面
    private String signUpUrl = "/defaultSignUp.html";

    //退出登陆
    private String signOutUrl;

    //自定义处理登陆请求默认异步
    private LoginType loginType = LoginType.JSON;

    //设置记住密码过期时间默认1小时
    private int rememberMeSeconds = 3600;


    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }
}
