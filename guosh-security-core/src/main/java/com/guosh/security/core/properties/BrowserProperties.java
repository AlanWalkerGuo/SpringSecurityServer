package com.guosh.security.core.properties;

public class BrowserProperties {

    //自定义登陆页面
    private String loginPage="/defaultLogin.html"; //如果用户没有配置登陆页面走默认

    //自定义处理登陆请求默认异步
    private LoginType loginType=LoginType.JSON;

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
}
