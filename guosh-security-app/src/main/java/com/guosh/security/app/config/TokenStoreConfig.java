package com.guosh.security.app.config;

import com.guosh.security.app.jwt.JwtTokenEnhancer;
import com.guosh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author: Guosh
 * @Date: 2019-06-21 11:14
 * 使用redis存储token
 */
@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //OAuth2 rendis存储token
    @Bean
    //配置项是redis就生效
    @ConditionalOnProperty(prefix = "guosh.security.oauth2", name = "storeType",havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }
    //OAuth2 JWT方式
    @Configuration
    //matchIfMissing如果没有这个配置项与配置项是jwt的时候都生效
    @ConditionalOnProperty(prefix = "guosh.security.oauth2", name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfig{
        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            //添加密签
            JwtAccessTokenConverter accessTokenConverter=new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return accessTokenConverter;
        }

        //往jwttoken里面加入自定义参数
        @Bean
        @ConditionalOnMissingBean(name="jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }

    }
}
