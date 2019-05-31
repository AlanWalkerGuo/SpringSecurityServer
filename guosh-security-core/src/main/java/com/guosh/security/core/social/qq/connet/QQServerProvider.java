package com.guosh.security.core.social.qq.connet;

import com.guosh.security.core.social.qq.api.QQ;
import com.guosh.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @Author: Guosh
 * @Date: 2019-05-29 14:41
 */
public class QQServerProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId;

    //导向认证服务器
    private static final String URL_AUTHORIZE="https://graph.qq.com/oauth2.0/authorize";

    //申请令牌
    private static final String URL_ACCESS_TOKEN="https://graph.qq.com/oauth2.0/token";

    public QQServerProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
