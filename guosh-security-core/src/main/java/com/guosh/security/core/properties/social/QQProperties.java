package com.guosh.security.core.properties.social;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @Author: Guosh
 * @Date: 2019-05-30 14:33
 */
public class QQProperties extends SocialProperties {
    //提供服务商ID
    private String providerId="qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
