package com.guosh.security.core.properties.OAuth2;

/**
 * @Author: Guosh
 * @Date: 2019-06-21 10:29
 */
public class OAuth2ClientProperties {
    private String clientId;
    private String clientSecrte;
    private int accessTokenValidateSeconds = 7200;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecrte() {
        return clientSecrte;
    }

    public void setClientSecrte(String clientSecrte) {
        this.clientSecrte = clientSecrte;
    }

    public int getAccessTokenValidateSeconds() {
        return accessTokenValidateSeconds;
    }

    public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
        this.accessTokenValidateSeconds = accessTokenValidateSeconds;
    }
}
