package com.guosh.security.core.properties.browser;

public class BrowserProperties {

    //自定义登陆页面
    private String loginPage = "/defaultLogin.html"; //如果用户没有配置登陆页面走默认

    //自定义注册页面
    private String signUpUrl = "/defaultSignUp.html";



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
}
