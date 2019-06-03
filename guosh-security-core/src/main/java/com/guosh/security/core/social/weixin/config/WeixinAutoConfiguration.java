/**
 * 
 */
package com.guosh.security.core.social.weixin.config;

import com.guosh.security.core.properties.SecurityProperties;
import com.guosh.security.core.properties.social.WinxinProperties;
import com.guosh.security.core.social.weixin.connet.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 微信登录配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "guosh.security.social.weixin", name = {"appId","appSecret"})
public class WeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
	 * #createConnectionFactory()
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		WinxinProperties weixinConfig = securityProperties.getSocial().getWeixin();
		return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

}
