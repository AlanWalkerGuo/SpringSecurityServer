package com.guosh.security.browser.domain;

/**
 * 通过第三方登陆注册页面返回的信息
 * @Author: Guosh
 * @Date: 2019-05-31 14:30
 */
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
