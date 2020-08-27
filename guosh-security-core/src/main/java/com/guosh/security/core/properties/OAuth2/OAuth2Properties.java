package com.guosh.security.core.properties.OAuth2;

/**
 * @Author: Guosh
 * @Date: 2019-06-21 10:28
 */
public class OAuth2Properties {
    //密钥签名
    private String jwtSigningKey="jwtdemo";

    //认证服务器授权的应用
    private OAuth2ClientProperties[] clients={};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

}
