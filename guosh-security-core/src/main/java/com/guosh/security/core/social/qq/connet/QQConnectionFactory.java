package com.guosh.security.core.social.qq.connet;

import com.guosh.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Author: Guosh
 * @Date: 2019-05-29 18:36
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServerProvider(appId,appSecret), new QQAdapter());
    }
}
