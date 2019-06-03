package com.guosh.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Author: Guosh
 * @Date: 2019-06-03 09:26
 */
public class WinxinProperties extends SocialProperties {
    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId="wixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
