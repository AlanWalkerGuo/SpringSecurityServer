package com.guosh.security.core.properties;

public class BrowserProperties {

    private String loginPage="/defaultLogin.html"; //如果用户没有配置登陆页面走默认

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
